/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.CancellationPolicyRepository;
import com.smsmode.pricing.dao.service.CancellationPolicyDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.CancellationPolicyModel;
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
 * <p>Created 16 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CancellationPolicyDaoServiceImpl implements CancellationPolicyDaoService {

    private final CancellationPolicyRepository cancellationPolicyRepository;

    @Override
    public boolean existsBy(Specification<CancellationPolicyModel> specification) {
        return cancellationPolicyRepository.exists(specification);
    }

    @Override
    public Page<CancellationPolicyModel> findAll(Specification<CancellationPolicyModel> specification, Pageable pageable) {
        return cancellationPolicyRepository.findAll(specification, pageable);
    }

    @Override
    public CancellationPolicyModel findOneBy(Specification<CancellationPolicyModel> specification) {
        return cancellationPolicyRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No cancellation policy found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.CANCELLATION_POLICY_NOT_FOUND,
                    "No cancellation policy found based on your criteria");
        });
    }

    @Override
    public void deleteBy(Specification<CancellationPolicyModel> specification) {
        cancellationPolicyRepository.delete(specification);
    }

    @Override
    public CancellationPolicyModel save(CancellationPolicyModel cancellationPolicyModel) {
        return cancellationPolicyRepository.save(cancellationPolicyModel);
    }
}
