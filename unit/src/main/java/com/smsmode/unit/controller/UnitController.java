/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.unit.SubUnitListResource;
import com.smsmode.unit.resource.unit.UnitGetResource;
import com.smsmode.unit.resource.unit.UnitItemGetResource;
import com.smsmode.unit.resource.unit.UnitPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Apr 2025</p>
 */
@RequestMapping("/units")
public interface UnitController {

    @GetMapping
    ResponseEntity<Page<UnitItemGetResource>> getAllUnits(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "nature", required = false) UnitNatureEnum nature,
            @RequestParam(value = "withParent", required = false) Boolean withParent,
            Pageable pageable);

    @GetMapping("/{unitId}")
    ResponseEntity<UnitGetResource> getUnitById(@PathVariable("unitId") String unitId);

    @PostMapping
    ResponseEntity<UnitGetResource> postUnit(@RequestBody @Valid UnitPostResource unitPostResource);

    @PostMapping("/{unitId}/sub-units")
    ResponseEntity<UnitItemGetResource> addSubUnitToMultiUnit(
            @PathVariable("unitId") String parentUnitId,
            @RequestBody @Valid SubUnitListResource subUnitListResource);

    @PatchMapping("/{subUnitId}/detach")
    ResponseEntity<Void> detachSubUnit(@PathVariable("subUnitId") String subUnitId);

    @GetMapping("/{unitId}/sub-units")
    ResponseEntity<Page<UnitItemGetResource>> getSubUnitsOfMultiUnit(
            @PathVariable("unitId") String unitId,
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable);

}
