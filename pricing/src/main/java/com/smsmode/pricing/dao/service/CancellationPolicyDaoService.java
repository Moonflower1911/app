/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.CancellationPolicyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
public interface CancellationPolicyDaoService {

    boolean existsBy(Specification<CancellationPolicyModel> specification);

    Page<CancellationPolicyModel> findAll(Specification<CancellationPolicyModel> specification, Pageable pageable);

    CancellationPolicyModel findOneBy(Specification<CancellationPolicyModel> specification);

    void deleteBy(Specification<CancellationPolicyModel> specification);

    CancellationPolicyModel save(CancellationPolicyModel cancellationPolicyModel);

}
