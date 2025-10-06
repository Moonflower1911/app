/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyGetResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyItemGetResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyPatchResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
@RequestMapping("additional-guest-strategies")
public interface AdditionalGuestRateStrategyController {

    @GetMapping
    ResponseEntity<Page<GuestRateStrategyItemGetResource>> getAll(@RequestParam(required = false) String search, @RequestParam(required = false) Boolean enabled, Pageable pageable);

    @GetMapping("/{guestRateStrategyId}")
    ResponseEntity<GuestRateStrategyGetResource> getById(@PathVariable("guestRateStrategyId") String guestRateStrategyId);

    @PostMapping
    ResponseEntity<GuestRateStrategyGetResource> post(@RequestBody GuestRateStrategyPostResource guestRateStrategyPostResource);

    @PatchMapping("/{guestRateStrategyId}")
    ResponseEntity<GuestRateStrategyGetResource> patchById(@PathVariable String guestRateStrategyId, @Valid @RequestBody GuestRateStrategyPatchResource guestRateStrategyPatchResource);

}
