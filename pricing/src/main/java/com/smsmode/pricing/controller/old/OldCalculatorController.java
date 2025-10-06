/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.old;

import com.smsmode.pricing.resource.old.calculate.BookingPostResource;
import com.smsmode.pricing.resource.old.calculate.UnitBookingRateGetResource;
import com.smsmode.pricing.resource.old.calculate.UnitFeePostResource;
import com.smsmode.pricing.resource.old.calculate.UnitFeeRateGetResource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Jul 2025</p>
 */
public interface OldCalculatorController {

    @PostMapping("/old/calculate")
    ResponseEntity<Map<String, UnitBookingRateGetResource>> postCalculate(@Valid @RequestBody BookingPostResource bookingPostResource);

    @PostMapping("/calculate-fee")
    ResponseEntity<UnitFeeRateGetResource> postFeeCalculate(@Valid @RequestBody UnitFeePostResource unitFeePostResource);
}
