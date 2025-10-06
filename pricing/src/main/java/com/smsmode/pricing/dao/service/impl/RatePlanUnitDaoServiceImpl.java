/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.RatePlanUnitRepository;
import com.smsmode.pricing.dao.service.RatePlanUnitDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.RatePlanUnitModel;
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
 * <p>Created 15 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatePlanUnitDaoServiceImpl implements RatePlanUnitDaoService {

    private final RatePlanUnitRepository ratePlanUnitRepository;

    @Override
    public Page<RatePlanUnitModel> findAll(Specification<RatePlanUnitModel> ratePlanUnitModelSpecification, Pageable pageable) {
        return ratePlanUnitRepository.findAll(ratePlanUnitModelSpecification, pageable);
    }

    @Override
    public RatePlanUnitModel save(RatePlanUnitModel rpUnit) {
        return ratePlanUnitRepository.save(rpUnit);
    }

    @Override
    public RatePlanUnitModel findOneBy(Specification<RatePlanUnitModel> ratePlanUnitModelSpecification) {
        return ratePlanUnitRepository.findOne(ratePlanUnitModelSpecification).orElseThrow(() -> {
            log.warn("No rate plan unit found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RATE_PLAN_UNIT_NOT_FOUND,
                    "No rate plan unit found based on your criteria");
        });
    }
}
