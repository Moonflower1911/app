/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.RateDaoService;
import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.dao.service.UnitRefDaoService;
import com.smsmode.pricing.dao.specification.RatePlanSpecification;
import com.smsmode.pricing.dao.specification.UnitRefSpecification;
import com.smsmode.pricing.mapper.RateMapper;
import com.smsmode.pricing.model.RateModel;
import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.model.UnitRefModel;
import com.smsmode.pricing.resource.rate.DailyRateGetResource;
import com.smsmode.pricing.resource.rate.RatePostResource;
import com.smsmode.pricing.resource.rate.UnitRateGetResource;
import com.smsmode.pricing.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 04 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateDaoService rateDaoService;
    private final RatePlanDaoService ratePlanDaoService;
    private final UnitRefDaoService unitRefDaoService;
    private final RateMapper rateMapper;

    @Override
    public ResponseEntity<List<UnitRateGetResource>> retrieveAll(String ratePlanId, LocalDate startDate, LocalDate endDate) {

        // build list of all dates in range
        List<LocalDate> allDates = startDate.datesUntil(endDate.plusDays(1))
                .toList();

        RatePlanModel ratePlanModel = ratePlanDaoService.findOneBy(RatePlanSpecification.withIdEqual(ratePlanId));

        // fetch existing rates
        List<RateModel> rates = rateDaoService.findRates(ratePlanId, ratePlanModel.getUnits().stream().map(UnitRefModel::getId).toList(), startDate, endDate);

        // group by unit + date
        Map<String, Map<LocalDate, RateModel>> rateMap = rates.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getUnit().getId(),
                        Collectors.toMap(RateModel::getDate, Function.identity())
                ));

        // build resources
        List<UnitRateGetResource> result = new ArrayList<>();
        for (UnitRefModel unit : ratePlanModel.getUnits()) {
            Map<LocalDate, RateModel> unitRates = rateMap.getOrDefault(unit.getId(), Map.of());

            List<DailyRateGetResource> dailyRates = allDates.stream()
                    .map(date -> {
                        RateModel rate = unitRates.get(date);
                        return new DailyRateGetResource(date,
                                rate != null ? (!ObjectUtils.isEmpty(rate.getSingleAdult()) ? rate.getSingleAdult() : BigDecimal.ZERO) : BigDecimal.ZERO,
                                rate != null ? (!ObjectUtils.isEmpty(rate.getDoubleAdult()) ? rate.getDoubleAdult() : BigDecimal.ZERO) : BigDecimal.ZERO,
                                rate != null ? (!ObjectUtils.isEmpty(rate.getExtraAdult()) ? rate.getExtraAdult() : BigDecimal.ZERO) : BigDecimal.ZERO,
                                rate != null ? (!ObjectUtils.isEmpty(rate.getExtraChild()) ? rate.getExtraChild() : BigDecimal.ZERO) : BigDecimal.ZERO);
                    })
                    .toList();

            result.add(new UnitRateGetResource(unit.getId(), unit.getName(), dailyRates));
        }

        return ResponseEntity.ok(result.stream().sorted(Comparator.comparing(UnitRateGetResource::getUnitName))
                .toList());
    }

    @Override
    @Transactional
    public ResponseEntity<List<UnitRateGetResource>> create(RatePostResource ratePostResource) {
        // fetch plan
        RatePlanModel ratePlanModel = ratePlanDaoService.findOneBy(RatePlanSpecification.withIdEqual(ratePostResource.getRatePlanId()));

        // fetch units
        List<UnitRefModel> unitRefModels = unitRefDaoService.findAllBy(UnitRefSpecification.withIdIn(ratePostResource.getUnitIds()), Pageable.unpaged()).getContent();

        // fetch existing rates
        List<RateModel> existingRates = rateDaoService.findRates(ratePlanModel.getId(), ratePlanModel.getUnits().stream().map(UnitRefModel::getId).toList(), ratePostResource.getStartDate(), ratePostResource.getEndDate());


        // group by unit+date
        Map<String, Map<LocalDate, RateModel>> rateMap = existingRates.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getUnit().getId(),
                        Collectors.toMap(RateModel::getDate, Function.identity())
                ));

        // iterate all units × dates
        List<RateModel> rates = new ArrayList<>();
        for (UnitRefModel unit : unitRefModels) {
            LocalDate current = ratePostResource.getStartDate();
            while (!current.isAfter(ratePostResource.getEndDate())) {
                RateModel existing = rateMap.getOrDefault(unit.getId(), Map.of()).get(current);
                if (existing != null) {
                    // update
                    if (!ObjectUtils.isEmpty(ratePostResource.getSingleAdult())) {
                        existing.setSingleAdult(ratePostResource.getSingleAdult());
                    }
                    if (!ObjectUtils.isEmpty(ratePostResource.getDoubleAdult())) {
                        existing.setDoubleAdult(ratePostResource.getDoubleAdult());
                    }
                    if (!ObjectUtils.isEmpty(ratePostResource.getExtraAdult())) {
                        existing.setExtraAdult(ratePostResource.getExtraAdult());
                    }
                    if (!ObjectUtils.isEmpty(ratePostResource.getExtraChild())) {
                        existing.setExtraChild(ratePostResource.getExtraChild());
                    }
                    rates.add(existing);
                } else {
                    // create
                    RateModel newRate = new RateModel();
                    newRate.setRatePlan(ratePlanModel);
                    newRate.setUnit(unit);
                    newRate.setDate(current);
                    newRate.setSingleAdult(ratePostResource.getSingleAdult());
                    newRate.setDoubleAdult(ratePostResource.getDoubleAdult());
                    newRate.setExtraAdult(ratePostResource.getExtraAdult());
                    newRate.setExtraChild(ratePostResource.getExtraChild());
                    rates.add(newRate);
                }
                current = current.plusDays(1);
            }
        }

        rates = rateDaoService.saveAll(rates);

        // group by unit
        Map<String, List<RateModel>> ratesByUnit = rates.stream()
                .collect(Collectors.groupingBy(r -> r.getUnit().getId()));

        // build resources
        return ResponseEntity.ok(unitRefModels.stream()
                .map(unit -> {
                    List<RateModel> unitRates = ratesByUnit.getOrDefault(unit.getId(), List.of());

                    List<DailyRateGetResource> dailyRates = unitRates.stream()
                            .sorted(Comparator.comparing(RateModel::getDate))
                            .map(rateMapper::modelToDailyRateGetResource)
                            .toList();

                    return new UnitRateGetResource(unit.getId(), unit.getName(), dailyRates);
                }).sorted(Comparator.comparing(UnitRateGetResource::getUnitName))
                .toList());
    }
}
