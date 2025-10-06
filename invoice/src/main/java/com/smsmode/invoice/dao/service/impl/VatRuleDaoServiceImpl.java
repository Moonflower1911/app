/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.dao.service.impl;

import com.smsmode.invoice.dao.repository.VatRuleRepository;
import com.smsmode.invoice.dao.service.VatRuleDaoService;
import com.smsmode.invoice.exception.ResourceNotFoundException;
import com.smsmode.invoice.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.invoice.model.VatRuleModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VatRuleDaoServiceImpl implements VatRuleDaoService {
    private final VatRuleRepository vatRuleRepository;

    @Override
    public VatRuleModel save(VatRuleModel vatRuleModel) {
        return vatRuleRepository.save(vatRuleModel);
    }

    @Override
    public VatRuleModel findOneBy(Specification<VatRuleModel> specification) {
        return vatRuleRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No VAT rule found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.VAT_RULE_NOT_FOUND,
                    "No VAT rule found based on your criteria");
        });
    }

    @Override
    public Page<VatRuleModel> findAllBy(Specification<VatRuleModel> specification, Pageable pageable) {
        return vatRuleRepository.findAll(specification,pageable);
    }

    @Override
    public void deleteBy(Specification<VatRuleModel> specification) {
        vatRuleRepository.delete(specification);
    }
}
