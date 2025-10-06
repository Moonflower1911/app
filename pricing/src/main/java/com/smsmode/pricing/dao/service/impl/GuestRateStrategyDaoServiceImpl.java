/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.GuestRateStrategyRepository;
import com.smsmode.pricing.dao.service.GuestRateStrategyDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.GuestRateStrategyModel;
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
 * <p>Created 06 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GuestRateStrategyDaoServiceImpl implements GuestRateStrategyDaoService {

    private final GuestRateStrategyRepository guestRateStrategyRepository;

    @Override
    public GuestRateStrategyModel save(GuestRateStrategyModel guestRateStrategyModel) {
        return guestRateStrategyRepository.save(guestRateStrategyModel);
    }

    @Override
    public Page<GuestRateStrategyModel> findAllBy(Specification<GuestRateStrategyModel> specification, Pageable pageable) {
        return guestRateStrategyRepository.findAll(specification, pageable);
    }

    @Override
    public GuestRateStrategyModel findOneBy(Specification<GuestRateStrategyModel> specification) {
        return guestRateStrategyRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No guest rate strategy found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.GUEST_RATE_STRATEGY_NOT_FOUND,
                    "No guest rate strategy found based on your criteria");
        });
    }
}
