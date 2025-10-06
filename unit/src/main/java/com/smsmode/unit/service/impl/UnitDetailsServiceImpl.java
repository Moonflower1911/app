/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import com.smsmode.unit.service.UnitDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitDetailsServiceImpl implements UnitDetailsService {

    private final UnitDaoService unitDaoService;
    private final UnitMapper unitMapper;

    @Override
    public ResponseEntity<UnitDetailsGetResource> retrieveDetailsById(String unitId) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        return ResponseEntity.ok(unitMapper.modelToDetailsGetResource(unit));
    }

    @Override
    @Transactional
    public ResponseEntity<UnitDetailsGetResource> update(String unitId, UnitDetailsPatchResource unitDetailsPatchResource) {
        boolean updateType = false;
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        if (!ObjectUtils.isEmpty(unitDetailsPatchResource.getType()) && !unitDetailsPatchResource.getType().equals(unit.getType())) {
            updateType = true;
        }
        unit = unitMapper.detailsPatchResourceToModel(unitDetailsPatchResource, unit);
        unit = unitDaoService.save(unit);
        if (unit.getNature().equals(UnitNatureEnum.MULTI_UNIT) && updateType) {
            unitDaoService.updateTypeByParentUnitId(unit.getId(), unit.getType());
        }
        return ResponseEntity.ok(unitMapper.modelToDetailsGetResource(unit));
    }
}
