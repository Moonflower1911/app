/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanItemGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@RequestMapping("rate-plans")
public interface RatePlanController {

    @GetMapping
    ResponseEntity<Page<RatePlanItemGetResource>> getByPage(@RequestParam(name = "search", required = false) String search,
                                                            Pageable pageable);

    @GetMapping("/{ratePlanId}")
    ResponseEntity<RatePlanGetResource> getById(@PathVariable("ratePlanId") String ratePlanId);

    @PostMapping
    ResponseEntity<RatePlanGetResource> post(@RequestBody @Valid RatePlanPostResource ratePlanPostResource);

    @PatchMapping("/{ratePlanId}")
    ResponseEntity<RatePlanGetResource> patchById(@PathVariable("ratePlanId") String ratePlanId,
                                                  @RequestBody @Valid RatePlanPatchResource ratePlanPatchResource);

}
