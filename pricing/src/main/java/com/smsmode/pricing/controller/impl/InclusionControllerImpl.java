/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.InclusionController;
import com.smsmode.pricing.resource.inclusion.InclusionItemGetResource;
import com.smsmode.pricing.resource.inclusion.InclusionPatchResource;
import com.smsmode.pricing.resource.inclusion.InclusionPostResource;
import com.smsmode.pricing.service.InclusionService;
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
 * <p>Created 14 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class InclusionControllerImpl implements InclusionController {

    private final InclusionService inclusionService;

    @Override
    public ResponseEntity<Page<InclusionItemGetResource>> getAll(String search, String ratePlanId, Boolean enabled, Pageable pageable) {
        return inclusionService.retrieveAll(search, ratePlanId, enabled, pageable);
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> getById(String inclusionId) {
        return inclusionService.retrieveById(inclusionId);
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> post(InclusionPostResource inclusionPostResource) {
        return inclusionService.create(inclusionPostResource);
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> patchById(String inclusionId, InclusionPatchResource inclusionPatchResource) {
        return inclusionService.updateById(inclusionId, inclusionPatchResource);
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> deleteById(String inclusionId) {
        return inclusionService.removeById(inclusionId);
    }
}
