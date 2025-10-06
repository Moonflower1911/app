/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.old.impl;

import com.smsmode.pricing.dao.old.service.DefaultRateDaoService;
import com.smsmode.pricing.dao.old.service.FeeDaoService;
import com.smsmode.pricing.dao.old.service.OldRatePlanDaoService;
import com.smsmode.pricing.dao.old.service.RateTableDaoService;
import com.smsmode.pricing.dao.old.specification.DefaultRateSpecification;
import com.smsmode.pricing.dao.old.specification.FeeSpecification;
import com.smsmode.pricing.dao.old.specification.OldRatePlanSpecification;
import com.smsmode.pricing.dao.old.specification.RateTableSpecification;
import com.smsmode.pricing.embeddable.AgeBucketEmbeddable;
import com.smsmode.pricing.enumeration.*;
import com.smsmode.pricing.mapper.old.FeeMapper;
import com.smsmode.pricing.model.old.*;
import com.smsmode.pricing.resource.old.calculate.*;
import com.smsmode.pricing.service.old.RateEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Aug 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateEngineServiceImpl implements RateEngineService {

    private final OldRatePlanDaoService oldRatePlanDaoService;
    private final RateTableDaoService rateTableDaoService;
    private final DefaultRateDaoService defaultRateDaoService;
    private final FeeDaoService feeDaoService;
    private final FeeMapper feeMapper;

    @Override
    public ResponseEntity<Map<String, UnitBookingRateGetResource>> calculateBookingRate(BookingPostResource bookingPostResource) {
        Map<String, UnitBookingRateGetResource> unitPricing = new HashMap<>();
        List<LocalDate> bookingDates = getDatesBetween(bookingPostResource.getCheckinDate(), bookingPostResource.getCheckoutDate());
        for (UnitOccupancyPostResource unit : bookingPostResource.getUnits()) {
            unitPricing.put(unit.getId(), this.calculateBookingRateForUnit(bookingDates, bookingPostResource, unit));
        }
        return ResponseEntity.ok(unitPricing);
    }

    @Override
    @Transactional
    public ResponseEntity<UnitFeeRateGetResource> calculateFeeBookingRate(UnitFeePostResource unitFeePostResource) {
        FeeModel feeModel = feeDaoService.findById(unitFeePostResource.getFeeId());
        UnitFeeRateGetResource unitFeeRateGetResource = new UnitFeeRateGetResource();
        unitFeeRateGetResource.setId(feeModel.getId());
        unitFeeRateGetResource.setName(feeModel.getName());
        unitFeeRateGetResource.setModality(feeModel.getModality());
        unitFeeRateGetResource.setAmount(feeModel.getAmount());
        unitFeeRateGetResource.setRequired(feeModel.isRequired());

        List<FeeItemGetResource> details = new ArrayList<>();
        //calculate price
        if (feeModel.getModality().equals(FeeModalityEnum.PER_PERSON) || feeModel.getModality().equals(FeeModalityEnum.PER_PERSON_PER_NIGHT)) {
            log.debug("Adding details for fee with modality per person ...");
            if (CollectionUtils.isEmpty(feeModel.getAdditionalGuestPrices())) {
                log.debug("No additional guests specified, will set details based on occupancy ...");
                FeeItemGetResource adultFeeItem = new FeeItemGetResource();
                adultFeeItem.setGuestType(GuestTypeEnum.ADULT);
                adultFeeItem.setQuantity(unitFeePostResource.getGuests().getAdults());
                adultFeeItem.setPrice(feeModel.getAmount());
                details.add(adultFeeItem);

                if (CollectionUtils.isEmpty(unitFeePostResource.getGuests().getChildren())) {
                    log.debug("No children specified");
                } else {
                    log.debug("Adding fee details for children ...");
                    FeeItemGetResource childrenFeeItem = new FeeItemGetResource();
                    childrenFeeItem.setGuestType(GuestTypeEnum.CHILD);
                    childrenFeeItem.setQuantity(unitFeePostResource.getGuests().getChildren().size());
                    childrenFeeItem.setPrice(feeModel.getAmount());
                    details.add(childrenFeeItem);
                }
//                unitFeeRateGetResource.setDetails(details);
            } else {
                Optional<AdditionalGuestFeeModel> adultFeeOptional = feeModel.getAdditionalGuestPrices().stream()
                        .filter(fee -> GuestTypeEnum.ADULT.equals(fee.getGuestType()))
                        .findFirst();
                if (adultFeeOptional.isEmpty()) {
                    FeeItemGetResource adultFeeItem = new FeeItemGetResource();
                    adultFeeItem.setGuestType(GuestTypeEnum.ADULT);
                    adultFeeItem.setQuantity(unitFeePostResource.getGuests().getAdults());
                    adultFeeItem.setPrice(feeModel.getAmount());
                    details.add(adultFeeItem);
                } else {

                    int guestCountThreshold = adultFeeOptional.get().getGuestCount(); // e.g., 1
                    GuestTypeEnum type = adultFeeOptional.get().getGuestType(); // e.g., ADULT
                    int totalAdults = unitFeePostResource.getGuests().getAdults();
                    int baseGuestCount = Math.min(guestCountThreshold, totalAdults);
                    if (baseGuestCount > 0) {
                        FeeItemGetResource baseItem = new FeeItemGetResource();
                        baseItem.setGuestType(type);
                        baseItem.setQuantity(baseGuestCount);
                        baseItem.setPrice(feeModel.getAmount());
                        details.add(baseItem);
                    }
                    int additionalGuestCount = Math.max(0, totalAdults - guestCountThreshold);
                    if (additionalGuestCount > 0) {
                        FeeItemGetResource extraItem = new FeeItemGetResource();
                        extraItem.setGuestType(type);
                        extraItem.setQuantity(additionalGuestCount);
                        if (adultFeeOptional.get().getAmountType().equals(AmountTypeEnum.FLAT)) {
                            extraItem.setPrice(adultFeeOptional.get().getValue());
                        } else {
                            extraItem.setPrice(feeModel.getAmount().multiply(adultFeeOptional.get().getValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
                        }
                        details.add(extraItem);
                    }
                }
                // Handle children with age buckets
                if (!CollectionUtils.isEmpty(unitFeePostResource.getGuests().getChildren())) {
                    List<AdditionalGuestFeeModel> childFeeModels = feeModel.getAdditionalGuestPrices().stream()
                            .filter(fee -> GuestTypeEnum.CHILD.equals(fee.getGuestType()))
                            .toList();
                    if (CollectionUtils.isEmpty(childFeeModels)) {
                        FeeItemGetResource adultFeeItem = new FeeItemGetResource();
                        adultFeeItem.setGuestType(GuestTypeEnum.CHILD);
                        adultFeeItem.setQuantity(unitFeePostResource.getGuests().getChildren().stream()
                                .mapToInt(ChildPostResource::getQuantity)
                                .sum());
                        adultFeeItem.setPrice(feeModel.getAmount());
                        details.add(adultFeeItem);
                    } else {
                        int unmatchedChildQuantity = 0;

                        for (ChildPostResource child : unitFeePostResource.getGuests().getChildren()) {
                            int childAge = child.getAge();
                            int childQty = child.getQuantity();

                            // Try to find a matching age bucket
                            Optional<AdditionalGuestFeeModel> matchingFeeOpt = childFeeModels.stream()
                                    .filter(fee -> {
                                        AgeBucketEmbeddable bucket = fee.getAgeBucket();
                                        return bucket != null && childAge >= bucket.getFromAge() && childAge <= bucket.getToAge();
                                    })
                                    .findFirst();

                            if (matchingFeeOpt.isPresent()) {
                                AdditionalGuestFeeModel matchingFee = matchingFeeOpt.get();
                                FeeItemGetResource feeItem = new FeeItemGetResource();
                                feeItem.setGuestType(GuestTypeEnum.CHILD);
                                feeItem.setAgeBucket(matchingFee.getAgeBucket());
                                feeItem.setQuantity(childQty);

                                if (matchingFee.getAmountType() == AmountTypeEnum.FLAT) {
                                    feeItem.setPrice(matchingFee.getValue());
                                } else {
                                    feeItem.setPrice(feeModel.getAmount()
                                            .multiply(matchingFee.getValue())
                                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
                                }

                                details.add(feeItem);
                            } else {
                                unmatchedChildQuantity += childQty;
                            }
                        }

                        // Add one entry for all unmatched children (no age bucket)
                        if (unmatchedChildQuantity > 0) {
                            FeeItemGetResource unmatchedFeeItem = new FeeItemGetResource();
                            unmatchedFeeItem.setGuestType(GuestTypeEnum.CHILD);
                            unmatchedFeeItem.setAgeBucket(null); // explicitly no age bucket
                            unmatchedFeeItem.setQuantity(unmatchedChildQuantity);
                            unmatchedFeeItem.setPrice(feeModel.getAmount());
                            details.add(unmatchedFeeItem);
                        }
                    }
                }
//                unitFeeRateGetResource.setDetails(details);
            }
        }

        if (!CollectionUtils.isEmpty(details)) {
            details = RateEngineServiceImpl.mergeGuestDetails(details);
        }

        if (feeModel.getModality().equals(FeeModalityEnum.PER_STAY)) {
            unitFeeRateGetResource.setRate(RateEngineServiceImpl.buildRateFromUnitPriceInclTax(feeModel.getAmount(), BigDecimal.valueOf(10), BigDecimal.valueOf(1)));
        } else if (feeModel.getModality().equals(FeeModalityEnum.PER_NIGHT)) {
            unitFeeRateGetResource.setRate(RateEngineServiceImpl.buildRateFromUnitPriceInclTax(feeModel.getAmount(), BigDecimal.valueOf(10), BigDecimal.valueOf(unitFeePostResource.getNights())));
        } else {
            List<BreakdownItemGetResource> breakdownItems = new ArrayList<>();
            for (FeeItemGetResource feeItem : details) {
                BreakdownItemGetResource breakdownItem = new BreakdownItemGetResource();
                breakdownItem.setGuestType(feeItem.getGuestType());
                breakdownItem.setAgeBucket(feeItem.getAgeBucket());
                breakdownItem.setQuantity(feeItem.getQuantity());

                BigDecimal quantity;
                if (feeModel.getModality().equals(FeeModalityEnum.PER_PERSON)) {
                    quantity = BigDecimal.valueOf(feeItem.getQuantity());
                } else {
                    quantity = BigDecimal.valueOf(feeItem.getQuantity()).multiply(BigDecimal.valueOf(unitFeePostResource.getNights()));
                }
                breakdownItem.setRate(RateEngineServiceImpl.buildRateFromUnitPriceInclTax(feeItem.getPrice(), BigDecimal.valueOf(10), quantity));
                breakdownItems.add(breakdownItem);
            }
            unitFeeRateGetResource.setBreakdown(breakdownItems);
            unitFeeRateGetResource.setRate(RateEngineServiceImpl.sumRates(unitFeeRateGetResource.getBreakdown()));
        }

/*
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (feeModel.getModality().equals(FeeModalityEnum.PER_STAY)) {
            totalAmount = feeModel.getAmount();
        } else if (feeModel.getModality().equals(FeeModalityEnum.PER_NIGHT)) {
            totalAmount = feeModel.getAmount().multiply(BigDecimal.valueOf(unitFeePostResource.getNights()));
        } else if (feeModel.getModality().equals(FeeModalityEnum.PER_PERSON)) {
            for (FeeItemGetResource item : unitFeeRateGetResource.getDetails()) {
                if (item.getQuantity() != null && item.getPrice() != null) {
                    BigDecimal lineTotal = BigDecimal.valueOf(item.getQuantity())
                            .multiply(item.getPrice());
                    totalAmount = totalAmount.add(lineTotal);
                }
            }
        } else {
            for (FeeItemGetResource item : unitFeeRateGetResource.getDetails()) {
                if (item.getQuantity() != null && item.getPrice() != null) {
                    BigDecimal lineTotal = BigDecimal.valueOf(item.getQuantity())
                            .multiply(item.getPrice());
                    lineTotal = lineTotal.multiply(BigDecimal.valueOf(unitFeePostResource.getNights()));
                    totalAmount = totalAmount.add(lineTotal);
                }
            }
        }
        if (!CollectionUtils.isEmpty(unitFeeRateGetResource.getDetails())) {
            unitFeeRateGetResource.setDetails(RateEngineServiceImpl.mergeGuestDetails(unitFeeRateGetResource.getDetails()));
        }*/


//        unitFeeRateGetResource.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
        return ResponseEntity.ok(unitFeeRateGetResource);
    }

    UnitBookingRateGetResource calculateBookingRateForUnit(List<LocalDate> bookingDates, BookingPostResource bookingPostResource, UnitOccupancyPostResource unit) {
        DefaultRateModel defaultRateModel = null;
        if (defaultRateDaoService.existsBy(DefaultRateSpecification.withUnitId(unit.getId()))) {
            log.debug("Retrieving default rate for unit with Id: {} from database ...", unit.getId());
            defaultRateModel = defaultRateDaoService.findOneBy(DefaultRateSpecification.withUnitId(unit.getId()));
        }
        String segmentId = null;
        if (!ObjectUtils.isEmpty(bookingPostResource.getSubSegmentId())) {
            segmentId = bookingPostResource.getSubSegmentId();
        } else if (!ObjectUtils.isEmpty(bookingPostResource.getSegmentId())) {
            segmentId = bookingPostResource.getSegmentId();
        }
        OldRatePlanModel oldRatePlanModel = null;
        if (ObjectUtils.isEmpty(segmentId)) {
            if (oldRatePlanDaoService.existsBy(OldRatePlanSpecification.withStandard(true).and(OldRatePlanSpecification.withUnitId(unit.getId())))) {
                oldRatePlanModel = oldRatePlanDaoService.findOneBy(OldRatePlanSpecification.withStandard(true).and(OldRatePlanSpecification.withUnitId(unit.getId())));
            }
        } else {
            if (oldRatePlanDaoService.existsBy(Specification.where(OldRatePlanSpecification.withEnabled(true)
                            .and(OldRatePlanSpecification.withUnitId(unit.getId())))
                    .and(OldRatePlanSpecification.withSegmentId(segmentId)))) {
                oldRatePlanModel = oldRatePlanDaoService.findOneBy(Specification.where(OldRatePlanSpecification.withEnabled(true)
                                .and(OldRatePlanSpecification.withUnitId(unit.getId())))
                        .and(OldRatePlanSpecification.withSegmentId(segmentId)));
            }
        }
        UnitBookingRateGetResource unitBookingRateGetResource = this.calculateBookingRateForUnitByPlan(bookingDates, bookingPostResource.getGuests(), oldRatePlanModel,
                defaultRateModel, bookingPostResource.getGlobalOccupancy(), unit.getOccupancy());
        List<FeeModel> feeModels = feeDaoService.findAllBy(FeeSpecification.withUnitId(unit.getId()).and(FeeSpecification.withEnabled(true)), Pageable.unpaged()).getContent();
        unitBookingRateGetResource.setFees(feeModels.stream().map(feeMapper::modelToItemGetResource).toList());
        return unitBookingRateGetResource;
    }

    private UnitBookingRateGetResource calculateBookingRateForUnitByPlan(List<LocalDate> bookingDates, GuestsPostResource guests, OldRatePlanModel ratePlan, DefaultRateModel defaultRateModel, BigDecimal globalOccupancy, BigDecimal unitOccupancy) {


        Map<LocalDate, BigDecimal> pricingPerDay = new HashMap<>();

        for (LocalDate date : bookingDates) {
            BigDecimal amount = calculatePricingPerPlanAndDate(date, guests, ratePlan, defaultRateModel, globalOccupancy, unitOccupancy);
            pricingPerDay.put(date, amount);
        }


        UnitBookingRateGetResource unitBookingRateGetResource = new UnitBookingRateGetResource();
        unitBookingRateGetResource.setPricingPerDay(pricingPerDay);

        unitBookingRateGetResource.setTotalPrice(RateEngineServiceImpl.buildRateFromUnitPriceInclTax(RateEngineServiceImpl.calculateAverage(pricingPerDay), BigDecimal.valueOf(10), BigDecimal.valueOf(bookingDates.size())));

        return unitBookingRateGetResource;
    }

    private BigDecimal calculatePricingPerPlanAndDate(LocalDate date, GuestsPostResource guests, OldRatePlanModel ratePlan, DefaultRateModel defaultRateModel, BigDecimal globalOccupancy, BigDecimal unitOccupancy) {
        BigDecimal amount = BigDecimal.valueOf(0);
        if (!ObjectUtils.isEmpty(ratePlan)) {
            if (rateTableDaoService.existsBy(RateTableSpecification.withRatePlanId(ratePlan.getId()).and(OldRatePlanSpecification.withType(RateTableTypeEnum.DYNAMIC)).and(RateTableSpecification.withDateWithinInclusive(date)))) {
                log.info("Found dynamic rate table handling date: {} in plan with id: {}", date, ratePlan.getId());
                log.debug("Will retrieve the dynamic table from database ...");
                RateTableModel rateTable = rateTableDaoService.findOneBy(RateTableSpecification.withRatePlanId(ratePlan.getId()).and(OldRatePlanSpecification.withType(RateTableTypeEnum.DYNAMIC)).and(RateTableSpecification.withDateWithinInclusive(date)));
                //calculate pricing for dynamic table
                amount = this.calculateRateForDynamicTable(date, guests, rateTable, globalOccupancy, unitOccupancy);

            } else if (rateTableDaoService.existsBy(RateTableSpecification.withRatePlanId(ratePlan.getId()).and(OldRatePlanSpecification.withType(RateTableTypeEnum.STANDARD)).and(RateTableSpecification.withDateWithinInclusive(date)))) {
                log.info("Found standard rate table handling date: {} in plan with id: {}", date, ratePlan.getId());
                log.debug("Will retrieve the standard table from database ...");
                RateTableModel rateTable = rateTableDaoService.findOneBy(RateTableSpecification.withRatePlanId(ratePlan.getId()).and(OldRatePlanSpecification.withType(RateTableTypeEnum.STANDARD)).and(RateTableSpecification.withDateWithinInclusive(date)));
                amount = this.calculateRateForDefaultAndStandardTable(date, guests, rateTable.getNightly(), rateTable.getDaySpecificRates(), rateTable.getAdditionalGuestFees());
            } else {
                if (!ObjectUtils.isEmpty(defaultRateModel)) {
                    amount = this.calculateRateForDefaultAndStandardTable(date, guests, defaultRateModel.getNightly(), defaultRateModel.getDaySpecificRates(), defaultRateModel.getAdditionalGuestFees());
                }
            }
        } else {
            if (!ObjectUtils.isEmpty(defaultRateModel)) {
                amount = this.calculateRateForDefaultAndStandardTable(date, guests, defaultRateModel.getNightly(), defaultRateModel.getDaySpecificRates(), defaultRateModel.getAdditionalGuestFees());
            }
        }
        return amount;
    }

    private BigDecimal calculateRateForDynamicTable(LocalDate date, GuestsPostResource guests, RateTableModel rateTable, BigDecimal globalOccupancy, BigDecimal unitOccupancy) {
        BigDecimal amount = BigDecimal.valueOf(0);
        BigDecimal nightly = BigDecimal.valueOf(0);
        BigDecimal occupancy = rateTable.getOccupancyMode().equals(OccupancyModeEnum.GLOBAL) ? globalOccupancy : unitOccupancy;

        if (!CollectionUtils.isEmpty(rateTable.getDaySpecificRates())) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            for (DaySpecificRateModel daySpecificRateModel : rateTable.getDaySpecificRates()) {
                if (!CollectionUtils.isEmpty(daySpecificRateModel.getDays()) && daySpecificRateModel.getDays().contains(dayOfWeek)) {
                    nightly = daySpecificRateModel.getNightly();
                    break;
                }
            }
        } else {
            if (occupancy.compareTo(BigDecimal.valueOf(rateTable.getLowestOccupancy())) < 0) {
                nightly = rateTable.getLowRate();
            } else if (occupancy.compareTo(BigDecimal.valueOf(rateTable.getMaxOccupancy())) > 0) {
                nightly = rateTable.getMaxRate();
            } else {
                BigDecimal rateDifference = rateTable.getMaxRate().subtract(rateTable.getLowRate());
                BigDecimal plusRateValue = rateDifference
                        .multiply(occupancy)
                        .divide(BigDecimal.valueOf(rateTable.getMaxOccupancy()), 2, RoundingMode.HALF_UP);
                nightly = rateTable.getLowRate().add(plusRateValue);
            }
        }
        amount = amount.add(nightly);
        if (!CollectionUtils.isEmpty(rateTable.getAdditionalGuestFees())) {
            log.debug("Default rate contains rules related to additional guest fees, will calculate the amount in case any guests matching ...");
            Optional<AdditionalGuestFeeModel> adultFeeOptional = rateTable.getAdditionalGuestFees().stream()
                    .filter(fee -> GuestTypeEnum.ADULT.equals(fee.getGuestType()))
                    .findFirst();
            if (adultFeeOptional.isPresent()) {
                log.debug("Adult Guest additional fee is specified, will calculate and update the new amount ...");
                int adultGuests = Math.max(0, guests.getAdults() - adultFeeOptional.get().getGuestCount());
                if (adultGuests >= 1) {
                    BigDecimal adultFeeAmount = getGuestFeeAmount(adultFeeOptional.get(), adultGuests, nightly);
                    amount = amount.add(adultFeeAmount);
                }
            }
            if (!CollectionUtils.isEmpty(guests.getChildren())) {
                log.debug("Children are specified within booking, will check if there's any child guest additional fee specified ...");
                boolean hasChildFee = rateTable.getAdditionalGuestFees().stream()
                        .anyMatch(fee -> fee.getGuestType() == GuestTypeEnum.CHILD);
                if (hasChildFee) {
                    log.debug("Child Guest additional fee is specified, will calculate and update the new amount if it matches ...");
                    List<AdditionalGuestFeeModel> childFees = rateTable.getAdditionalGuestFees().stream()
                            .filter(fee -> fee.getGuestType() == GuestTypeEnum.CHILD).toList();
                    log.debug("Will loop over children guests to calculate and update nightly rate ...");
                    for (ChildPostResource childPostResource : guests.getChildren()) {
                        log.debug("Processing child entry: {} ...", childPostResource);
                        int age = childPostResource.getAge();
                        Optional<AdditionalGuestFeeModel> matchingFeeOpt = childFees.stream()
                                .filter(fee -> {
                                    AgeBucketEmbeddable ageBucket = fee.getAgeBucket();
                                    return age >= ageBucket.getFromAge() && age <= ageBucket.getToAge();
                                })
                                .findFirst();
                        if (matchingFeeOpt.isPresent()) {
                            int childGuests = Math.max(0, (childPostResource.getQuantity() + 1) - matchingFeeOpt.get().getGuestCount());
                            if (childGuests >= 1) {
                                BigDecimal childFeeAmount = getGuestFeeAmount(matchingFeeOpt.get(), childGuests, nightly);
                                amount = amount.add(childFeeAmount);
                            }
                        } else {
                            log.debug("No fee rule found for child age {}, skipping.", age);
                        }

                    }
                }
            }
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRateForDefaultAndStandardTable(LocalDate date, GuestsPostResource guests, BigDecimal nightly,
                                                               List<DaySpecificRateModel> daySpecificRateModels, List<AdditionalGuestFeeModel> additionalGuestFeeModels) {
        BigDecimal amount = BigDecimal.valueOf(0);
        if (!CollectionUtils.isEmpty(daySpecificRateModels)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            for (DaySpecificRateModel daySpecificRateModel : daySpecificRateModels) {
                if (!CollectionUtils.isEmpty(daySpecificRateModel.getDays()) && daySpecificRateModel.getDays().contains(dayOfWeek)) {
                    nightly = daySpecificRateModel.getNightly();
                    break;
                }
            }
        }
        amount = amount.add(nightly);
        if (!CollectionUtils.isEmpty(additionalGuestFeeModels)) {
            log.debug("Default rate contains rules related to additional guest fees, will calculate the amount in case any guests matching ...");
            Optional<AdditionalGuestFeeModel> adultFeeOptional = additionalGuestFeeModels.stream()
                    .filter(fee -> GuestTypeEnum.ADULT.equals(fee.getGuestType()))
                    .findFirst();
            if (adultFeeOptional.isPresent()) {
                log.debug("Adult Guest additional fee is specified, will calculate and update the new amount ...");
                int adultGuests = Math.max(0, guests.getAdults() - adultFeeOptional.get().getGuestCount());
                if (adultGuests >= 1) {
                    BigDecimal adultFeeAmount = getGuestFeeAmount(adultFeeOptional.get(), adultGuests, nightly);
                    amount = amount.add(adultFeeAmount);
                }
            }
            if (!CollectionUtils.isEmpty(guests.getChildren())) {
                log.debug("Children are specified within booking, will check if there's any child guest additional fee specified ...");
                boolean hasChildFee = additionalGuestFeeModels.stream()
                        .anyMatch(fee -> fee.getGuestType() == GuestTypeEnum.CHILD);
                if (hasChildFee) {
                    log.debug("Child Guest additional fee is specified, will calculate and update the new amount if it matches ...");
                    List<AdditionalGuestFeeModel> childFees = additionalGuestFeeModels.stream()
                            .filter(fee -> fee.getGuestType() == GuestTypeEnum.CHILD).toList();
                    log.debug("Will loop over children guests to calculate and update nightly rate ...");
                    for (ChildPostResource childPostResource : guests.getChildren()) {
                        log.debug("Processing child entry: {} ...", childPostResource);
                        int age = childPostResource.getAge();
                        Optional<AdditionalGuestFeeModel> matchingFeeOpt = childFees.stream()
                                .filter(fee -> {
                                    AgeBucketEmbeddable ageBucket = fee.getAgeBucket();
                                    return age >= ageBucket.getFromAge() && age <= ageBucket.getToAge();
                                })
                                .findFirst();
                        if (matchingFeeOpt.isPresent()) {
                            int childGuests = Math.max(0, (childPostResource.getQuantity() + 1) - matchingFeeOpt.get().getGuestCount());
                            if (childGuests >= 1) {
                                BigDecimal childFeeAmount = getGuestFeeAmount(matchingFeeOpt.get(), childGuests, nightly);
                                amount = amount.add(childFeeAmount);
                            }
                        } else {
                            log.debug("No fee rule found for child age {}, skipping.", age);
                        }

                    }
                }
            }
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal getGuestFeeAmount(AdditionalGuestFeeModel guestFee, Integer guestCount, BigDecimal nightly) {
        BigDecimal guestFeeAmount;
        if (guestFee.getAmountType().equals(AmountTypeEnum.FLAT)) {
            guestFeeAmount = guestFee.getValue().multiply(BigDecimal.valueOf(guestCount));
        } else {
            guestFeeAmount = nightly.multiply(guestFee.getValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(guestCount));
        }
        return guestFeeAmount;
    }


    public static List<LocalDate> getDatesBetween(LocalDate checkinDate, LocalDate checkoutDate) {
        if (checkinDate == null || checkoutDate == null || !checkinDate.isBefore(checkoutDate)) {
            return List.of(); // return empty list for invalid input
        }
        return checkinDate.datesUntil(checkoutDate).toList();
    }

    public static BigDecimal calculateTotal(Map<LocalDate, BigDecimal> pricingPerDay) {
        return pricingPerDay.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateAverage(Map<LocalDate, BigDecimal> pricingPerDay) {
        if (pricingPerDay.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = calculateTotal(pricingPerDay);
        return total
                .divide(new BigDecimal(pricingPerDay.size()), 2, RoundingMode.HALF_UP);
    }

    public static RateGetResource buildRateFromUnitPriceInclTax(BigDecimal unitAveragePriceInclTax, BigDecimal vatPercentage, BigDecimal quantity) {
        // Ensure scale for percentage
        vatPercentage = vatPercentage.setScale(2, RoundingMode.HALF_UP);

        // Calculate VAT factor
        BigDecimal vatFactor = vatPercentage.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);

        // Calculate unit price excl. tax
        BigDecimal unitPriceExclTax = unitAveragePriceInclTax.divide(BigDecimal.ONE.add(vatFactor), 6, RoundingMode.HALF_UP);

        // Calculate VAT amount per unit
        BigDecimal vatAmount = unitAveragePriceInclTax.subtract(unitPriceExclTax);

        // Total excl. tax
        BigDecimal amountExclTax = unitPriceExclTax.multiply(quantity);

        // Total incl. tax
        BigDecimal amountInclTax = unitAveragePriceInclTax.multiply(quantity);

        // Build object
        RateGetResource rate = new RateGetResource();
        rate.setUnitPriceExclTax(unitPriceExclTax.setScale(2, RoundingMode.HALF_UP));
        rate.setQuantity(quantity.setScale(2, RoundingMode.HALF_UP));
        rate.setVatPercentage(vatPercentage);
        rate.setVatAmount(vatAmount.setScale(2, RoundingMode.HALF_UP));
        rate.setUnitPriceInclTax(unitAveragePriceInclTax.setScale(2, RoundingMode.HALF_UP));
        rate.setAmountExclTax(amountExclTax.setScale(2, RoundingMode.HALF_UP));
        rate.setAmountInclTax(amountInclTax.setScale(2, RoundingMode.HALF_UP));

        return rate;
    }

    public static List<FeeItemGetResource> mergeGuestDetails(List<FeeItemGetResource> items) {
        return new ArrayList<>(
                items.stream()
                        .collect(Collectors.toMap(
                                item -> item,
                                FeeItemGetResource::getQuantity,
                                Integer::sum // merge quantities if same key
                        ))
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            FeeItemGetResource merged = entry.getKey();
                            merged.setQuantity(entry.getValue());
                            return merged;
                        })
                        .toList()
        );
    }

    public static RateGetResource sumRates(List<BreakdownItemGetResource> items) {
        RateGetResource total = new RateGetResource();

        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmountExclTax = BigDecimal.ZERO;
        BigDecimal totalAmountInclTax = BigDecimal.ZERO;
        BigDecimal totalVatAmount = BigDecimal.ZERO;

        for (BreakdownItemGetResource item : items) {
            RateGetResource rate = item.getRate();
            if (rate != null && rate.getUnitPriceExclTax() != null
                && rate.getUnitPriceExclTax().compareTo(BigDecimal.ZERO) > 0) {

                // Sum quantities
                if (rate.getQuantity() != null) {
                    totalQuantity = totalQuantity.add(rate.getQuantity());
                }

                // Sum amounts
                if (rate.getAmountExclTax() != null) {
                    totalAmountExclTax = totalAmountExclTax.add(rate.getAmountExclTax());
                }
                if (rate.getAmountInclTax() != null) {
                    totalAmountInclTax = totalAmountInclTax.add(rate.getAmountInclTax());
                }
                if (rate.getVatAmount() != null) {
                    totalVatAmount = totalVatAmount.add(rate.getVatAmount());
                }
            }
        }

        // Fill total resource
        total.setQuantity(totalQuantity);
        total.setAmountExclTax(totalAmountExclTax);
        total.setAmountInclTax(totalAmountInclTax);
        total.setVatAmount(totalVatAmount);

        // Recalculate unit price excl/incl tax (avoid division by zero)
        if (totalQuantity.compareTo(BigDecimal.ZERO) > 0) {
            total.setUnitPriceExclTax(totalAmountExclTax.divide(totalQuantity, 2, RoundingMode.HALF_UP));
            total.setUnitPriceInclTax(totalAmountInclTax.divide(totalQuantity, 2, RoundingMode.HALF_UP));
        } else {
            total.setUnitPriceExclTax(BigDecimal.ZERO);
            total.setUnitPriceInclTax(BigDecimal.ZERO);
        }

        // Keep VAT percentage from first non-free item if present
        for (BreakdownItemGetResource item : items) {
            RateGetResource rate = item.getRate();
            if (rate != null && rate.getUnitPriceExclTax() != null
                && rate.getUnitPriceExclTax().compareTo(BigDecimal.ZERO) > 0
                && rate.getVatPercentage() != null) {
                total.setVatPercentage(rate.getVatPercentage());
                break;
            }
        }

        return total;
    }

}
