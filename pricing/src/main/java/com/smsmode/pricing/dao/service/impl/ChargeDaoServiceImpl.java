/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.ChargeRepository;
import com.smsmode.pricing.dao.service.ChargeDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.ChargeModel;
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
 * <p>Created 10 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeDaoServiceImpl implements ChargeDaoService {

    private final ChargeRepository chargeRepository;

    @Override
    public Page<ChargeModel> findAll(Specification<ChargeModel> specification, Pageable pageable) {
        return chargeRepository.findAll(specification, pageable);
    }

    @Override
    public ChargeModel findOneBy(Specification<ChargeModel> specification) {
        return chargeRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No charge found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RATE_PLAN_NOT_FOUND,
                    "No charge found based on your criteria");
        });
    }

    @Override
    public void deleteBy(Specification<ChargeModel> specification) {
        chargeRepository.delete(specification);
    }

    @Override
    public ChargeModel save(ChargeModel chargeModel) {
        return chargeRepository.save(chargeModel);
    }

    @Override
    public boolean existsBy(Specification<ChargeModel> specification) {
        return chargeRepository.exists(specification);
    }
}
