/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitGetResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPatchResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Sep 2025</p>
 */
@RequestMapping("rate-plan-units")
public interface RatePlanUnitController {

    @GetMapping
    ResponseEntity<Page<RatePlanUnitGetResource>> getByPage(@RequestParam(name = "ratePlanId") String ratePlanId, Pageable pageable);

    @PostMapping
    ResponseEntity<RatePlanUnitGetResource> post(@RequestBody RatePlanUnitPostResource ratePlanUnitPostResource);

    @PatchMapping("/{ratePlanUnitId}")
    ResponseEntity<RatePlanUnitGetResource> patchById(@PathVariable String ratePlanUnitId, @RequestBody RatePlanUnitPatchResource ratePlanUnitPatchResource);

}
