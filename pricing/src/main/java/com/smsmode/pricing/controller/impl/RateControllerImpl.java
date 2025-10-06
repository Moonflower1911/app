/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.RateController;
import com.smsmode.pricing.resource.rate.RatePostResource;
import com.smsmode.pricing.resource.rate.UnitRateGetResource;
import com.smsmode.pricing.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 04 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RateControllerImpl implements RateController {

    private final RateService rateService;

    @Override
    public ResponseEntity<List<UnitRateGetResource>> getAll(String ratePlanId, LocalDate startDate, LocalDate endDate) {
        return rateService.retrieveAll(ratePlanId, startDate, endDate);
    }

    @Override
    public ResponseEntity<List<UnitRateGetResource>> postUnitRate(RatePostResource ratePostResource) {
        return rateService.create(ratePostResource);
    }
}
