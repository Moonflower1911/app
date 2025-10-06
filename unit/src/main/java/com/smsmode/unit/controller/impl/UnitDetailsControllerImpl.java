/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.UnitDetailsController;
import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import com.smsmode.unit.service.UnitDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Jun 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class UnitDetailsControllerImpl implements UnitDetailsController {

    private final UnitDetailsService unitDetailsService;

    @Override
    public ResponseEntity<UnitDetailsGetResource> getUnitInfosById(String unitId) {
        return unitDetailsService.retrieveDetailsById(unitId);
    }

    @Override
    public ResponseEntity<UnitDetailsGetResource> patchUnitDetailsById(String unitId, UnitDetailsPatchResource unitDetailsPatchResource) {
        return unitDetailsService.update(unitId, unitDetailsPatchResource);
    }
}
