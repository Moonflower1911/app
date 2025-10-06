/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.ChargeController;
import com.smsmode.pricing.enumeration.ChargeTypeEnum;
import com.smsmode.pricing.resource.charge.ChargeItemGetResource;
import com.smsmode.pricing.resource.charge.ChargePatchResource;
import com.smsmode.pricing.resource.charge.ChargePostResource;
import com.smsmode.pricing.service.ChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChargeControllerImpl implements ChargeController {

    private final ChargeService chargeService;

    @Override
    public ResponseEntity<Page<ChargeItemGetResource>> getAll(String search, Boolean isExtra, Boolean isPackage, Boolean enabled,
                                                              Pageable pageable) {
        return chargeService.retrieveAll(search, isExtra, isPackage, enabled, pageable);
    }

    @Override
    public ResponseEntity<ChargeItemGetResource> getById(String chargeId) {
        return chargeService.retrieveById(chargeId);
    }

    @Override
    public ResponseEntity<ChargeItemGetResource> post(ChargePostResource chargePostResource) {
        return chargeService.create(chargePostResource);
    }

    @Override
    public ResponseEntity<ChargeItemGetResource> patchById(String chargeId, ChargePatchResource chargePatchResource) {
        return chargeService.updateById(chargeId, chargePatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String chargeId) {
        return chargeService.deleteById(chargeId);
    }
}
