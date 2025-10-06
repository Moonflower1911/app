package com.smsmode.pricing.controller.old.impl;

import com.smsmode.pricing.controller.old.OldRatePlanController;
import com.smsmode.pricing.resource.old.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPostResource;
import com.smsmode.pricing.service.old.OldRatePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of RatePlanController for managing rate plan REST endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OldRatePlanControllerImpl implements OldRatePlanController {

    private final OldRatePlanService oldRatePlanService;

    @Override
    public ResponseEntity<RatePlanGetResource> create(RatePlanPostResource ratePlanPostResource) {
        return oldRatePlanService.create(ratePlanPostResource);
    }

    @Override
    public ResponseEntity<Page<RatePlanGetResource>> getAll(String unitId, String search, String segmentName, Pageable pageable) {
        log.debug("GET /rate-plans - Retrieving all rate plans with pagination");
        return oldRatePlanService.getAll(unitId, search, segmentName, pageable);
    }

    @Override
    public ResponseEntity<RatePlanGetResource> getById(String ratePlanId) {
        log.debug("GET /rate-plans/{} - Retrieving rate plan by ID", ratePlanId);
        return oldRatePlanService.getById(ratePlanId);
    }

    @Override
    public ResponseEntity<RatePlanGetResource> update(String ratePlanId, RatePlanPatchResource ratePlanPatchResource) {
        log.debug("PATCH /rate-plans/{} - Updating rate plan", ratePlanId);
        return oldRatePlanService.update(ratePlanId, ratePlanPatchResource);
    }

    @Override
    public ResponseEntity<Void> delete(String ratePlanId) {
        log.debug("DELETE /rate-plans/{} - Deleting rate plan", ratePlanId);
        return oldRatePlanService.delete(ratePlanId);
    }
}