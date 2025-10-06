/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service.impl;

import com.smsmode.unit.dao.projection.FlatUnitBedProjection;
import com.smsmode.unit.dao.projection.UnitSubCountProjection;
import com.smsmode.unit.dao.repository.UnitRepository;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.transaction.Transactional;
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
 * <p>Created 11 Apr 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitDaoServiceImpl implements UnitDaoService {

    private final UnitRepository unitRepository;


    @Override
    public UnitModel save(UnitModel unitModel) {
        return unitRepository.save(unitModel);
    }

    @Override
    public Page<UnitModel> findAllBy(Specification<UnitModel> specification, Pageable pageable) {
        return unitRepository.findAll(specification, pageable);
    }

    @Override
    public UnitModel findOneBy(Specification<UnitModel> specification) {
        return unitRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any unit with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.UNIT_NOT_FOUND,
                            "No unit found with the specified criteria");
                });
    }

    @Override
    public UnitModel findById(String unitId) {
        return unitRepository.findById(unitId).orElseThrow(() -> {
            log.debug("Unit with ID [{}] not found", unitId);
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.UNIT_NOT_FOUND,
                    "Unit with ID [" + unitId + "] not found");
        });
    }

    @Override
    public List<UnitModel> findByParentUnit(UnitModel parent) {
        return unitRepository.findAll((root, query, cb) ->
                cb.equal(root.get(UnitModel_.parent), parent));
    }

    @Override
    public void updateTypeByParentUnitId(String parentUnitId, UnitTypeEnum type) {
        unitRepository.updateTypeByParentUnitId(parentUnitId, type);
    }

    @Override
    @Transactional
    public void saveAll(List<UnitModel> subUnits) {
        unitRepository.saveAll(subUnits);
    }

    @Override
    public Page<UnitModel> findAvailableUnits(String[] reservedUnitIdsArray, Pageable pageable) {
        return unitRepository.findAvailableUnits(reservedUnitIdsArray, pageable);
    }

    @Override
    public List<UnitSubCountProjection> countSubUnitsForMultiUnits(List<String> multiUnitIds) {
        return unitRepository.countSubUnitsFor(multiUnitIds);
    }

    @Override
    public List<FlatUnitBedProjection> findUnitBeds(List<String> unitIds, List<RoomTypeEnum> roomTypes) {
        return unitRepository.findUnitBeds(unitIds, roomTypes);
    }
}
