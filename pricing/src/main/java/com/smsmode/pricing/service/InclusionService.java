/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.inclusion.InclusionItemGetResource;
import com.smsmode.pricing.resource.inclusion.InclusionPatchResource;
import com.smsmode.pricing.resource.inclusion.InclusionPostResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Pageable;


/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
public interface InclusionService {
    ResponseEntity<Page<InclusionItemGetResource>> retrieveAll(String search, String ratePlanId, Boolean enabled, Pageable pageable);

    ResponseEntity<InclusionItemGetResource> retrieveById(String inclusionId);

    ResponseEntity<InclusionItemGetResource> create(InclusionPostResource inclusionPostResource);

    ResponseEntity<InclusionItemGetResource> updateById(String inclusionId, InclusionPatchResource inclusionPatchResource);

    ResponseEntity<InclusionItemGetResource> removeById(String inclusionId);

}
