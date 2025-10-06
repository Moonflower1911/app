/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.charge.ChargeItemGetResource;
import com.smsmode.pricing.resource.charge.ChargePatchResource;
import com.smsmode.pricing.resource.charge.ChargePostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
@RequestMapping("charges")
public interface ChargeController {

    @GetMapping
    ResponseEntity<Page<ChargeItemGetResource>> getAll(@RequestParam(required = false) String search,
                                                       @RequestParam(required = false) Boolean isExtra,
                                                       @RequestParam(required = false) Boolean isPackage,
                                                       @RequestParam(required = false) Boolean enabled,
                                                       Pageable pageable);

    @GetMapping("/{chargeId}")
    ResponseEntity<ChargeItemGetResource> getById(@PathVariable("chargeId") String chargeId);

    @PostMapping
    ResponseEntity<ChargeItemGetResource> post(@Valid @RequestBody ChargePostResource chargePostResource);

    @PatchMapping("/{chargeId}")
    ResponseEntity<ChargeItemGetResource> patchById(@PathVariable("chargeId") String chargeId, @RequestBody ChargePatchResource chargePatchResource);

    @DeleteMapping("/{chargeId}")
    ResponseEntity<Void> deleteById(@PathVariable("chargeId") String chargeId);
}
