/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.RatePlanChargeRepository;
import com.smsmode.pricing.dao.service.RatePlanChargeDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.RatePlanChargeModel;
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
 * <p>Created 14 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatePlanChargeDaoServiceImpl implements RatePlanChargeDaoService {

    private final RatePlanChargeRepository ratePlanChargeRepository;

    @Override
    public Page<RatePlanChargeModel> findAll(Specification<RatePlanChargeModel> specification, Pageable pageable) {
        return ratePlanChargeRepository.findAll(specification, pageable);
    }

    @Override
    public RatePlanChargeModel findOneBy(Specification<RatePlanChargeModel> specification) {
        return ratePlanChargeRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No inclusion found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.INCLUSION_NOT_FOUND,
                    "No inclusion found based on your criteria");
        });
    }

    @Override
    public RatePlanChargeModel save(RatePlanChargeModel ratePlanChargeModel) {
        return ratePlanChargeRepository.save(ratePlanChargeModel);
    }

    @Override
    public void removeBy(Specification<RatePlanChargeModel> specification) {
        ratePlanChargeRepository.delete(specification);
    }
}
