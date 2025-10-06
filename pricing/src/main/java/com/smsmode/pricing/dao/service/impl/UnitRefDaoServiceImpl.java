/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.UnitRefRepository;
import com.smsmode.pricing.dao.service.UnitRefDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.UnitRefModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitRefDaoServiceImpl implements UnitRefDaoService {

    private final UnitRefRepository unitRefRepository;

    @Override
    public Page<UnitRefModel> findAllBy(Specification<UnitRefModel> specification, Pageable pageable) {
        return unitRefRepository.findAll(specification, pageable);
    }

    @Override
    public List<UnitRefModel> saveAll(List<UnitRefModel> unitRefs) {
        return unitRefRepository.saveAll(unitRefs);
    }

    @Override
    public boolean existsBy(Specification<UnitRefModel> specification) {
        return unitRefRepository.exists(specification);
    }

    @Override
    public UnitRefModel findOneBy(Specification<UnitRefModel> specification) {

        return unitRefRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any unit with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.UNIT_REF_NOT_FOUND,
                            "No unit found with the specified criteria");
                });
    }

    @Override
    public UnitRefModel save(UnitRefModel unitRefModel) {
        return unitRefRepository.save(unitRefModel);
    }
}
