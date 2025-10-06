/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.AdditionalGuestRateStrategyController;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyGetResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyItemGetResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyPatchResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyPostResource;
import com.smsmode.pricing.service.GuestRateStrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdditionalGuestRateStrategyControllerImpl implements AdditionalGuestRateStrategyController {

    private final GuestRateStrategyService guestRateStrategyService;

    @Override
    public ResponseEntity<Page<GuestRateStrategyItemGetResource>> getAll(String search, Boolean enabled, Pageable pageable) {
        return guestRateStrategyService.retrieveAll(search, enabled, pageable);
    }

    @Override
    public ResponseEntity<GuestRateStrategyGetResource> getById(String guestRateStrategyId) {
        return guestRateStrategyService.retrieveById(guestRateStrategyId);
    }

    @Override
    public ResponseEntity<GuestRateStrategyGetResource> post(GuestRateStrategyPostResource guestRateStrategyPostResource) {
        return guestRateStrategyService.create(guestRateStrategyPostResource);
    }

    @Override
    public ResponseEntity<GuestRateStrategyGetResource> patchById(String guestRateStrategyId, GuestRateStrategyPatchResource guestRateStrategyPatchResource) {
        return guestRateStrategyService.update(guestRateStrategyId, guestRateStrategyPatchResource);
    }
}
