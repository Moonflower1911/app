/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Apr 2025</p>
 */
@RequestMapping("/units/{unitId}/infos")
public interface UnitInfoController {

    @GetMapping
    ResponseEntity<UnitInfosGetResource> getUnitInfosById(@PathVariable String unitId);

    @PatchMapping
    ResponseEntity<UnitInfosGetResource> patchUnitById(@PathVariable("unitId") String unitId, @RequestBody UnitInfosPatchResource unitInfosPatchResource);

}
