/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.dao.service;

import com.smsmode.invoice.model.VatRuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
public interface VatRuleDaoService {

    VatRuleModel save(VatRuleModel vatRuleModel);

    VatRuleModel findOneBy(Specification<VatRuleModel> specification);

    Page<VatRuleModel> findAllBy(Specification<VatRuleModel> specification, Pageable pageable);

    void deleteBy(Specification<VatRuleModel> vatRuleModelSpecification);

}
