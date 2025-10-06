/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import com.smsmode.unit.service.UnitInfosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 May 2025</p>
 */
@Service
@RequiredArgsConstructor
public class UnitInfosServiceImpl implements UnitInfosService {

    private final UnitDaoService unitDaoService;
    private final UnitMapper unitMapper;

    @Override
    public ResponseEntity<UnitInfosGetResource> update(String unitId, UnitInfosPatchResource unitInfosPatchResource) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));

        unit = unitMapper.infosPatchResourceToModel(unitInfosPatchResource, unit);

        unit = unitDaoService.save(unit);

        return ResponseEntity.ok(unitMapper.modelToInfosGetResource(unit));
    }

    @Override
    public ResponseEntity<UnitInfosGetResource> retrieveById(String unitId) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        return ResponseEntity.ok(unitMapper.modelToInfosGetResource(unit));
    }
}
