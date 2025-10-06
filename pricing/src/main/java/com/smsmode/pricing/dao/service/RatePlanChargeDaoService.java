/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.RatePlanChargeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
public interface RatePlanChargeDaoService {
    Page<RatePlanChargeModel> findAll(Specification<RatePlanChargeModel> specification, Pageable pageable);

    RatePlanChargeModel findOneBy(Specification<RatePlanChargeModel> ratePlanChargeModelSpecification);

    RatePlanChargeModel save(RatePlanChargeModel ratePlanChargeModel);

    void removeBy(Specification<RatePlanChargeModel> specification);
}
