/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Apr 2025</p>
 */
@RequestMapping("/units/{unitId}/details")
public interface UnitDetailsController {

    @GetMapping
    ResponseEntity<UnitDetailsGetResource> getUnitInfosById(@PathVariable String unitId);

    @PatchMapping
    ResponseEntity<UnitDetailsGetResource> patchUnitDetailsById(@PathVariable("unitId") String unitId, @RequestBody UnitDetailsPatchResource unitDetailsPatchResource);

}
