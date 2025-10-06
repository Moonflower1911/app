/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.RatePlanModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public interface RatePlanDaoService {

    RatePlanModel save(RatePlanModel ratePlanModel);

    RatePlanModel findOneBy(Specification<RatePlanModel> specification);

    Page<RatePlanModel> findAllBy(Specification<RatePlanModel> specification, Pageable pageable);

    boolean existsBy(Specification<RatePlanModel> specification);

}
