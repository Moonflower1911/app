/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.PropertySettingsDaoService;
import com.smsmode.pricing.dao.service.RateDaoService;
import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.dao.specification.RatePlanSpecification;
import com.smsmode.pricing.model.PropertySettingsModel;
import com.smsmode.pricing.model.RateModel;
import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.resource.calculator.BookingPostResource;
import com.smsmode.pricing.resource.calculator.DatedRateGetResource;
import com.smsmode.pricing.resource.calculator.RatePlanPriceGetResource;
import com.smsmode.pricing.resource.old.calculate.ChildPostResource;
import com.smsmode.pricing.resource.old.calculate.GuestsPostResource;
import com.smsmode.pricing.service.CalculatorService;
import com.smsmode.pricing.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {
    private final RatePlanDaoService ratePlanDaoService;
    private final RateDaoService rateDaoService;
    private final PropertySettingsDaoService propertySettingsDaoService;


    @Override
    public ResponseEntity<Map<String, List<RatePlanPriceGetResource>>> calculateBooking(BookingPostResource bookingPostResource) {
        log.info("Received booking request: checkin={}, checkout={}, guests={}",
                bookingPostResource.getCheckinDate(),
                bookingPostResource.getCheckoutDate(),
                bookingPostResource.getGuests());

        Map<String, List<RatePlanPriceGetResource>> rateCalculationResponse = new HashMap<>();
        Integer numberOfNights = DateUtils.calculateNights(bookingPostResource.getCheckinDate(), bookingPostResource.getCheckoutDate());
        Integer numberOfDays = DateUtils.calculateDays(bookingPostResource.getCheckinDate(), bookingPostResource.getCheckoutDate());
        log.info("Number of nights is: {} and number of days is: {}", numberOfNights, numberOfDays);
        Specification<RatePlanModel> specification = RatePlanSpecification.withEnabledEqual(true)
                .and(RatePlanSpecification.withUnitIdIn(bookingPostResource.getUnits()))
                .and(RatePlanSpecification.withMinLosLessThanOrEqual(numberOfNights))
                .and(RatePlanSpecification.withMaxLosIsNullOrGreaterThanOrEqual(numberOfNights))
                .and(RatePlanSpecification.withMinLeadLessThanOrEqual(numberOfDays))
                .and(RatePlanSpecification.withMaxLeadIsNullOrGreaterThanOrEqual(numberOfDays));
        log.debug("Will retrieve rate plans from database that are enabled and linked to the following units: {}", bookingPostResource.getUnits());
        List<RatePlanModel> ratePlans = ratePlanDaoService.findAllBy(specification, Pageable.unpaged()).getContent();
        log.info("Found {} rate plans that matches the criteria", ratePlans.size());
        log.debug("Retrieve property settings from database ...");
        Page<PropertySettingsModel> propertySettingsModels = propertySettingsDaoService.findAllBy(null, Pageable.unpaged());
        for (RatePlanModel ratePlanModel : ratePlans) {
            for (String unitId : bookingPostResource.getUnits()) {
                if (ratePlanModel.getUnits().stream().anyMatch(unit -> unit.getId().equals(unitId))) {
                    if (!rateCalculationResponse.containsKey(unitId)) {
                        rateCalculationResponse.put(unitId, new ArrayList<>());
                    }
                    rateCalculationResponse.get(unitId).add(calculateRateBooking(unitId, ratePlanModel, bookingPostResource, propertySettingsModels.getContent().getFirst()));
                }
            }
        }


        return ResponseEntity.ok(rateCalculationResponse);
    }

    private RatePlanPriceGetResource calculateRateBooking(String unitId, RatePlanModel ratePlanModel, BookingPostResource bookingPostResource, PropertySettingsModel propertySettingsModel) {
        // fetch available rates for the requested dates
        List<RateModel> rateModels = rateDaoService.findRates(
                ratePlanModel.getId(),
                List.of(unitId),
                bookingPostResource.getCheckinDate(),
                bookingPostResource.getCheckoutDate()
        );

        // build a lookup map for faster access: date -> rateModel
        Map<LocalDate, RateModel> rateByDate = rateModels.stream()
                .collect(Collectors.toMap(RateModel::getDate, r -> r));

        // get all nights between checkin (inclusive) and checkout (exclusive)
        List<LocalDate> stayDates = DateUtils.getDatesBetween(
                bookingPostResource.getCheckinDate(),
                bookingPostResource.getCheckoutDate()
        );

        List<DatedRateGetResource> dailyRates = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // extract guests
        GuestsPostResource guests = bookingPostResource.getGuests();
        int adults = guests.getAdults() != null ? guests.getAdults() : 0;

        int chargeableChildren = 0;
        int childrenAsAdults = 0;

        if (guests.getChildren() != null) {
            for (ChildPostResource child : guests.getChildren()) {
                int age = child.getAge();
                int qty = child.getQuantity();

                if (age < propertySettingsModel.getChildMinAge()) {
                    // free → skip
                    continue;
                } else if (age <= propertySettingsModel.getChildMaxAge()) {
                    chargeableChildren += qty;
                } else {
                    // older than childMaxAge → extra adults
                    childrenAsAdults += qty;
                }
            }
        }

        // final adult count includes adults + children above childMaxAge
        adults += childrenAsAdults;

        for (LocalDate date : stayDates) {
            RateModel rateModel = rateByDate.get(date);
            BigDecimal nightlyRate = BigDecimal.ZERO;

            if (rateModel != null) {
                // --- Adults pricing ---
                if (adults == 1) {
                    nightlyRate = nightlyRate.add(safe(rateModel.getSingleAdult()));
                } else if (adults == 2) {
                    if (rateModel.getDoubleAdult() != null) {
                        nightlyRate = nightlyRate.add(rateModel.getDoubleAdult());
                    } else {
                        // fallback: use single + one extra
                        nightlyRate = nightlyRate.add(safe(rateModel.getSingleAdult()));
                        nightlyRate = nightlyRate.add(safe(rateModel.getExtraAdult()));
                    }
                } else if (adults > 2) {
                    if (rateModel.getDoubleAdult() != null) {
                        nightlyRate = nightlyRate.add(rateModel.getDoubleAdult());
                        int extraAdults = adults - 2;
                        nightlyRate = nightlyRate.add(safe(rateModel.getExtraAdult())
                                .multiply(BigDecimal.valueOf(extraAdults)));
                    } else {
                        // fallback: use single + (adults - 1) extra
                        nightlyRate = nightlyRate.add(safe(rateModel.getSingleAdult()));
                        int extraAdults = adults - 1;
                        nightlyRate = nightlyRate.add(safe(rateModel.getExtraAdult())
                                .multiply(BigDecimal.valueOf(extraAdults)));
                    }
                }

                // --- Children pricing (bucket only) ---
                if (chargeableChildren > 0) {
                    nightlyRate = nightlyRate.add(
                            safe(rateModel.getExtraChild())
                                    .multiply(BigDecimal.valueOf(chargeableChildren))
                    );
                }
            }

            // add nightly rate to the list
            dailyRates.add(new DatedRateGetResource(date, nightlyRate));

            // accumulate total
            total = total.add(nightlyRate);
        }

        // calculate average
        BigDecimal avgRateNight = dailyRates.isEmpty()
                ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(dailyRates.size()), 2, RoundingMode.HALF_UP);

        // build and return resource
        RatePlanPriceGetResource resource = new RatePlanPriceGetResource();
        resource.setId(ratePlanModel.getId());
        resource.setName(ratePlanModel.getName());
        resource.setDescription(ratePlanModel.getDescription());
        resource.setTotalBookingRate(total);
        resource.setAvgRateNight(avgRateNight);
        resource.setDailyRates(dailyRates);

        return resource;
    }

    /**
     * Utility to avoid NPE on nullable BigDecimal.
     */
    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

}
