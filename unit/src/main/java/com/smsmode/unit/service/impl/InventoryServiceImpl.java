package com.smsmode.unit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smsmode.unit.dao.projection.FlatUnitBedProjection;
import com.smsmode.unit.dao.projection.UnitSubCountProjection;
import com.smsmode.unit.dao.service.RoomDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.exception.InternalServerException;
import com.smsmode.unit.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.inventory.get.AvailabilityGetResource;
import com.smsmode.unit.resource.inventory.get.UnitInventoryGetResource;
import com.smsmode.unit.resource.inventory.post.InventoryPostResource;
import com.smsmode.unit.resource.pricing.BookingPostResource;
import com.smsmode.unit.resource.pricing.RatePlanPriceGetResource;
import com.smsmode.unit.service.InventoryService;
import com.smsmode.unit.service.feign.BookingFeignService;
import com.smsmode.unit.service.feign.PricingFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final BookingFeignService bookingFeignService;
    private final UnitDaoService unitDaoService;
    private final PricingFeignService pricingFeignService;
    private final RoomDaoService roomDaoService;
    private final UnitMapper unitMapper;

    @Override
    public ResponseEntity<Page<UnitInventoryGetResource>> getInventory(InventoryPostResource inventoryPostResource, Pageable pageable) {
        log.debug("Fetching reserved units from booking service...");
        List<String> reservedUnitIds;
        try {
            ResponseEntity<List<String>> response = bookingFeignService.getBookedUnits(inventoryPostResource.getCheckinDate(), inventoryPostResource.getCheckoutDate());
            reservedUnitIds = response.getBody();
            if (reservedUnitIds == null) reservedUnitIds = Collections.emptyList();
            log.info("Reserved unit IDs: {}", reservedUnitIds);
        } catch (Exception e) {
            log.error("Failed to fetch reserved units", e);
            throw new InternalServerException(InternalServerExceptionTitleEnum.SERVICE_UNAVAILABLE, "Booking service unavailable");
        }
        log.debug("Constructing array from reserved unit IDs ...");
        String[] reservedUnitIdsArray = reservedUnitIds.toArray(new String[0]);
        log.debug("Fetching available units from database based on readiness and reserved unit Ids array ...");
        Page<UnitModel> availableUnits = unitDaoService.findAvailableUnits(reservedUnitIdsArray, pageable);
        log.info("Retrieved {} units available", availableUnits.getTotalElements());
        log.debug("Mapping models to unit inventory get resource ...");
        Page<UnitInventoryGetResource> inventoryGetResources = availableUnits.map(unitMapper::modelToInventoryGetResource);
        log.info("Mapping successful. Result contains: {}", inventoryGetResources.getTotalElements());
        log.debug("Grouping reserved unit ids retrieved from booking service with count ...");
        Map<String, Long> reservedUnitCountGrouped = reservedUnitIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        log.info("Grouping result: {}", reservedUnitCountGrouped);
        log.debug("Filtering the available units to keep only ids of those with MULTI_UNIT nature ...");
        List<String> multiUnitIds = availableUnits.getContent().stream()
                .filter(unit -> UnitNatureEnum.MULTI_UNIT.equals(unit.getNature()))
                .map(UnitModel::getId)
                .toList();
        log.info("Multi unit ids from your available units are: {}", multiUnitIds);
        log.debug("Will retrieve how many sub-units each multi unit has from database ...");
        List<UnitSubCountProjection> unitSubCountProjections = unitDaoService.countSubUnitsForMultiUnits(multiUnitIds);
        log.info("Result of subUnitCount is: {}", unitSubCountProjections);
        Map<String, Long> subUnitCountMap = unitSubCountProjections.stream()
                .collect(Collectors.toMap(UnitSubCountProjection::getUnitId, UnitSubCountProjection::getSubUnitCount));
        log.debug("Filter available unit models to retrieve list of their ids ...");
        List<String> availableUnitIds = availableUnits.map(AbstractBaseModel::getId).stream().toList();
        log.debug("Retrieve beds related to each unit of the resource from database ...");
        List<FlatUnitBedProjection> flatResults = unitDaoService.findUnitBeds(availableUnitIds, Stream.of(RoomTypeEnum.BEDROOM, RoomTypeEnum.LIVING, RoomTypeEnum.GENERAL).toList());
        log.info("Retrieved unit beds as flat results");
        log.debug("Grouping beds by unitId ...");
        Map<String, List<BedEmbeddable>> groupedBedsByUnit = flatResults.stream()
                .collect(Collectors.groupingBy(
                        FlatUnitBedProjection::getUnitId,
                        Collectors.mapping(FlatUnitBedProjection::getBed, Collectors.toList())
                ));
        log.debug("Enrich each resource in the page with availability and beds ...");
        inventoryGetResources.forEach(resource -> {
            log.debug("Resource with id:{} and name: {} ...", resource.getId(), resource.getName());
            String unitId = resource.getId();
            AvailabilityGetResource availability = new AvailabilityGetResource();

            if (UnitNatureEnum.MULTI_UNIT.equals(resource.getNature())) {
                log.debug("Resource is of type MULTI_UNIT ...");
                Long totalSubUnits = subUnitCountMap.getOrDefault(unitId, 0L);
                Long reservedCount = reservedUnitCountGrouped.getOrDefault(unitId, 0L);
                availability.setQuantity(totalSubUnits);
                availability.setAvailable(Math.max(totalSubUnits - reservedCount, 0));
                log.info("Availability after calculation is: {}", availability);
            } else {
                log.debug("Resource is of type SINGLE ...");
                availability.setQuantity(1L);
                availability.setAvailable(1L);
                log.info("Availability is: {}", availability);
            }
            log.debug("Setting availability to resource ...");
            resource.setAvailability(availability);
            log.debug("Setting beds ...");
            resource.setBeds(groupedBedsByUnit.getOrDefault(unitId, null));
        });
        // Step 3: Call pricing service
        BookingPostResource bookingPostResource = new BookingPostResource();
        bookingPostResource.setCheckinDate(inventoryPostResource.getCheckinDate());
        bookingPostResource.setCheckoutDate(inventoryPostResource.getCheckoutDate());
        bookingPostResource.setGuests(inventoryPostResource.getGuests());

        Set<String> unitIds = availableUnits.getContent().stream()
                .map(UnitModel::getId)
                .collect(Collectors.toSet());

        bookingPostResource.setUnits(unitIds);

        log.info("Calling pricing service with checkin: {}, checkout: {}",
                inventoryPostResource.getCheckinDate(),
                inventoryPostResource.getCheckoutDate());

        if (!inventoryPostResource.getCheckoutDate().isAfter(inventoryPostResource.getCheckinDate())) {
            throw new IllegalArgumentException("Checkout date must be after check-in date");
        }

        ResponseEntity<Map<String, List<RatePlanPriceGetResource>>> pricingResponse =
                pricingFeignService.postCalculate(bookingPostResource);

        if (pricingResponse.getStatusCode().is2xxSuccessful() && pricingResponse.getBody() != null) {
            Map<String, List<RatePlanPriceGetResource>> ratesByUnit = pricingResponse.getBody();

            log.debug("Enriching each resource with pricing details ...");

            inventoryGetResources.forEach(resource -> {
                List<RatePlanPriceGetResource> ratePlans = ratesByUnit.get(resource.getId());

                if (ratePlans != null && !ratePlans.isEmpty()) {
                    // Pick the first rate plan (or choose based on business logic)
                    RatePlanPriceGetResource selectedPlan = ratePlans.get(0);

                    resource.setTotalBookingRate(selectedPlan.getTotalBookingRate());
                    resource.setAvgRateNight(selectedPlan.getAvgRateNight());
                    resource.setDailyRates(selectedPlan.getDailyRates());

                    log.info("Applied rate plan '{}' for unit {} (total={}, avgNight={})",
                            selectedPlan.getName(), resource.getId(),
                            selectedPlan.getTotalBookingRate(), selectedPlan.getAvgRateNight());
                } else {
                    log.warn("No pricing data returned for unit {}", resource.getId());
                }
            });
        } else {
            log.warn("Pricing service returned an unexpected response: {}", pricingResponse.getStatusCode());
        }
        return ResponseEntity.ok(inventoryGetResources);
    }
}
