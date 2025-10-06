/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.RatePlanController;
import com.smsmode.pricing.resource.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanItemGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPostResource;
import com.smsmode.pricing.service.RatePlanService;
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
 * <p>Created 01 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RatePlanControllerImpl implements RatePlanController {

    private final RatePlanService ratePlanService;

    @Override
    public ResponseEntity<Page<RatePlanItemGetResource>> getByPage(String search, Pageable pageable) {
        return ratePlanService.retrieveAll(search, pageable);
    }

    @Override
    public ResponseEntity<RatePlanGetResource> getById(String ratePlanId) {
        return ratePlanService.retrieveById(ratePlanId);
    }

    @Override
    public ResponseEntity<RatePlanGetResource> post(RatePlanPostResource ratePlanPostResource) {
        return ratePlanService.create(ratePlanPostResource);
    }

    @Override
    public ResponseEntity<RatePlanGetResource> patchById(String ratePlanId, RatePlanPatchResource ratePlanPatchResource) {
        return ratePlanService.update(ratePlanId, ratePlanPatchResource);
    }
}
