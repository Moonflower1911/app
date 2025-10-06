/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyGetResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPatchResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
public interface CancellationPolicyService {
    ResponseEntity<Page<CancellationPolicyGetResource>> retrieveAll(String search, Boolean enabled, Pageable pageable);

    ResponseEntity<CancellationPolicyGetResource> retrieveById(String cancellationPolicyId);

    ResponseEntity<CancellationPolicyGetResource> create(CancellationPolicyPostResource cancellationPolicyPostResource);

    ResponseEntity<CancellationPolicyGetResource> updateById(String cancellationPolicyId, CancellationPolicyPatchResource cancellationPolicyPatchResource);

    ResponseEntity<Void> removeById(String cancellationPolicyId);

}
