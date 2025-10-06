/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.controller.impl;

import com.smsmode.invoice.controller.VatRuleController;
import com.smsmode.invoice.resource.vatrule.VatRuleGetResource;
import com.smsmode.invoice.resource.vatrule.VatRulePatchResource;
import com.smsmode.invoice.resource.vatrule.VatRulePostResource;
import com.smsmode.invoice.service.VatRuleService;
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
 * <p>Created 09 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class VatRuleControllerImpl implements VatRuleController {

    private final VatRuleService vatRuleService;

    @Override
    public ResponseEntity<Page<VatRuleGetResource>> getByPage(String search, Pageable pageable) {
        return vatRuleService.retrieveAll(search, pageable);
    }

    @Override
    public ResponseEntity<VatRuleGetResource> post(VatRulePostResource vatRulePostResource) {
        return vatRuleService.create(vatRulePostResource);
    }

    @Override
    public ResponseEntity<VatRuleGetResource> patchById(String vatRuleId, VatRulePatchResource vatRulePatchResource) {
        return vatRuleService.updateById(vatRuleId, vatRulePatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String vatRuleId) {
        return vatRuleService.removeById(vatRuleId);
    }
}
