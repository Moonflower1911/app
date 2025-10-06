/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.RatePlanUnitController;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitGetResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPatchResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPostResource;
import com.smsmode.pricing.service.RatePlanUnitService;
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
 * <p>Created 15 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RatePlanUnitControllerImpl implements RatePlanUnitController {

    private final RatePlanUnitService ratePlanUnitService;

    @Override
    public ResponseEntity<Page<RatePlanUnitGetResource>> getByPage(String ratePlanId, Pageable pageable) {
        return ratePlanUnitService.retrieveAll(ratePlanId, pageable);
    }

    @Override
    public ResponseEntity<RatePlanUnitGetResource> post(RatePlanUnitPostResource ratePlanUnitPostResource) {
        return ratePlanUnitService.create(ratePlanUnitPostResource);
    }

    @Override
    public ResponseEntity<RatePlanUnitGetResource> patchById(String ratePlanUnitId, RatePlanUnitPatchResource ratePlanUnitPatchResource) {
        return ratePlanUnitService.updateById(ratePlanUnitId, ratePlanUnitPatchResource);
    }
}
