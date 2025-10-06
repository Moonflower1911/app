/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitGetResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPatchResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Sep 2025</p>
 */
public interface RatePlanUnitService {
    ResponseEntity<Page<RatePlanUnitGetResource>> retrieveAll(String ratePlanId, Pageable pageable);

    ResponseEntity<RatePlanUnitGetResource> create(RatePlanUnitPostResource ratePlanUnitPostResource);

    ResponseEntity<RatePlanUnitGetResource> updateById(String ratePlanUnitId, RatePlanUnitPatchResource ratePlanUnitPatchResource);

}
