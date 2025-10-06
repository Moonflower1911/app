/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.UnitInfoController;
import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import com.smsmode.unit.service.UnitInfosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 May 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class UnitInfoControllerImpl implements UnitInfoController {

    private final UnitInfosService unitInfosService;

    @Override
    public ResponseEntity<UnitInfosGetResource> getUnitInfosById(String unitId) {
        return unitInfosService.retrieveById(unitId);
    }

    @Override
    public ResponseEntity<UnitInfosGetResource> patchUnitById(String unitId, UnitInfosPatchResource unitInfosPatchResource) {
        return unitInfosService.update(unitId, unitInfosPatchResource);
    }
}
