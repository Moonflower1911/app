/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.dao.service.impl;

import com.smsmode.booking.dao.repository.FeeRepository;
import com.smsmode.booking.dao.service.FeeDaoService;
import com.smsmode.booking.exception.ResourceNotFoundException;
import com.smsmode.booking.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.booking.model.FeeModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeeDaoServiceImpl implements FeeDaoService {

    private final FeeRepository feeRepository;

    @Override
    public Page<FeeModel> findAllBy(Specification<FeeModel> specification, Pageable pageable) {
        return feeRepository.findAll(specification, pageable);
    }

    @Override
    public FeeModel findOneBy(Specification<FeeModel> specification) {
        return feeRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any fee with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.FEE_NOT_FOUND,
                            "No fee found with the specified criteria");
                });
    }

    @Override
    public boolean existsBy(Specification<FeeModel> feeModelSpecification) {
        return feeRepository.exists(feeModelSpecification);
    }

    @Override
    public FeeModel save(FeeModel feeModel) {
        return feeRepository.save(feeModel);
    }


    @Override
    public void deleteBy(Specification<FeeModel> specification) {
        feeRepository.delete(specification);
    }

    @Override
    public void deleteByBookingId(String bookingId) {
        feeRepository.deleteByBookingId(bookingId);
    }

    @Override
    public void deleteAll(Collection<FeeModel> feeModels) {
        feeRepository.deleteAll(feeModels);
    }


}
