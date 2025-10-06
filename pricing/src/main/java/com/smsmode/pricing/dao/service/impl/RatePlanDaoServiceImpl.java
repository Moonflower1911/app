/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.RatePlanRepository;
import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.RatePlanModel;
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
 * <p>Created 01 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatePlanDaoServiceImpl implements RatePlanDaoService {

    private final RatePlanRepository ratePlanRepository;

    @Override
    public RatePlanModel save(RatePlanModel ratePlanModel) {
        return ratePlanRepository.save(ratePlanModel);
    }

    @Override
    public RatePlanModel findOneBy(Specification<RatePlanModel> specification) {
        return ratePlanRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No rate plan found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RATE_PLAN_NOT_FOUND,
                    "No rate plan found based on your criteria");
        });
    }

    @Override
    public Page<RatePlanModel> findAllBy(Specification<RatePlanModel> specification, Pageable pageable) {
        return ratePlanRepository.findAll(specification, pageable);
    }

    @Override
    public boolean existsBy(Specification<RatePlanModel> specification) {
        return ratePlanRepository.exists(specification);
    }
}
