/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.old.impl;

import com.smsmode.pricing.controller.old.OldCalculatorController;
import com.smsmode.pricing.resource.old.calculate.BookingPostResource;
import com.smsmode.pricing.resource.old.calculate.UnitBookingRateGetResource;
import com.smsmode.pricing.resource.old.calculate.UnitFeePostResource;
import com.smsmode.pricing.resource.old.calculate.UnitFeeRateGetResource;
import com.smsmode.pricing.service.old.RateEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Jul 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class OldCalculatorControllerImpl implements OldCalculatorController {

    private final RateEngineService rateEngineService;

    @Override
    public ResponseEntity<Map<String, UnitBookingRateGetResource>> postCalculate(BookingPostResource bookingPostResource) {
        return rateEngineService.calculateBookingRate(bookingPostResource);
    }

    @Override
    public ResponseEntity<UnitFeeRateGetResource> postFeeCalculate(UnitFeePostResource unitFeePostResource) {
        return rateEngineService.calculateFeeBookingRate(unitFeePostResource);
    }
}
