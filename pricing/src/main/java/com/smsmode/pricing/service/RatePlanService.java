/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.model.UnitRefModel;
import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanItemGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public interface RatePlanService {

 ResponseEntity<RatePlanGetResource> create(RatePlanPostResource ratePlanPostResource);

 ResponseEntity<RatePlanGetResource> update(String ratePlanId, RatePlanPatchResource ratePlanPatchResource);

 List<UnitRefModel> ensureUnitsExist(List<ResourceCodeRefGetResource> unitRefs);

 ResponseEntity<Page<RatePlanItemGetResource>> retrieveAll(String search, Pageable pageable);

 ResponseEntity<RatePlanGetResource> retrieveById(String ratePlanId);

}
