/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.CalculatorController;
import com.smsmode.pricing.resource.calculator.BookingPostResource;
import com.smsmode.pricing.resource.calculator.RatePlanPriceGetResource;
import com.smsmode.pricing.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CalculatorControllerImpl implements CalculatorController {

    private final CalculatorService calculatorService;

    @Override
    public ResponseEntity<Map<String, List<RatePlanPriceGetResource>>> postCalculate(BookingPostResource bookingPostResource) {
        return calculatorService.calculateBooking(bookingPostResource);
    }
}
