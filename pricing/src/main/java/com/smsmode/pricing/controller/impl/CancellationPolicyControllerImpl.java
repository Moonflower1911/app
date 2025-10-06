/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.CancellationPolicyController;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyGetResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPatchResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPostResource;
import com.smsmode.pricing.service.CancellationPolicyService;
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
 * <p>Created 16 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CancellationPolicyControllerImpl implements CancellationPolicyController {

    private final CancellationPolicyService cancellationPolicyService;

    @Override
    public ResponseEntity<Page<CancellationPolicyGetResource>> getAll(String search, Boolean enabled, Pageable pageable) {
        return cancellationPolicyService.retrieveAll(search, enabled, pageable);
    }

    @Override
    public ResponseEntity<CancellationPolicyGetResource> getById(String cancellationPolicyId) {
        return cancellationPolicyService.retrieveById(cancellationPolicyId);
    }

    @Override
    public ResponseEntity<CancellationPolicyGetResource> post(CancellationPolicyPostResource cancellationPolicyPostResource) {
        return cancellationPolicyService.create(cancellationPolicyPostResource);
    }

    @Override
    public ResponseEntity<CancellationPolicyGetResource> patchById(String cancellationPolicyId, CancellationPolicyPatchResource cancellationPolicyPatchResource) {
        return cancellationPolicyService.updateById(cancellationPolicyId, cancellationPolicyPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String cancellationPolicyId) {
        return cancellationPolicyService.removeById(cancellationPolicyId);
    }
}
