/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.rate.RatePostResource;
import com.smsmode.pricing.resource.rate.UnitRateGetResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 04 Sep 2025</p>
 */
@RequestMapping("rates")
public interface RateController {

    @GetMapping
    ResponseEntity<List<UnitRateGetResource>> getAll(@RequestParam String ratePlanId, @RequestParam LocalDate startDate,
                                                     @RequestParam LocalDate endDate);

    @PostMapping
    ResponseEntity<List<UnitRateGetResource>> postUnitRate(@RequestBody RatePostResource ratePostResource);
}
