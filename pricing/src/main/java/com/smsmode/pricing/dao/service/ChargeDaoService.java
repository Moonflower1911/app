/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.ChargeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
public interface ChargeDaoService {
    Page<ChargeModel> findAll(Specification<ChargeModel> specification, Pageable pageable);

    ChargeModel findOneBy(Specification<ChargeModel> chargeModelSpecification);

    void deleteBy(Specification<ChargeModel> chargeModelSpecification);

    ChargeModel save(ChargeModel chargeModel);

    boolean existsBy(Specification<ChargeModel> specification);

}
