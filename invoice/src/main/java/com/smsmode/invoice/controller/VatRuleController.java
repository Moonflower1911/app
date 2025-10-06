/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.controller;

import com.smsmode.invoice.resource.vatrule.VatRuleGetResource;
import com.smsmode.invoice.resource.vatrule.VatRulePatchResource;
import com.smsmode.invoice.resource.vatrule.VatRulePostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
@RequestMapping("vat-rules")
public interface VatRuleController {
    @GetMapping
    ResponseEntity<Page<VatRuleGetResource>> getByPage(@RequestParam(required = false) String search, Pageable pageable);

    @PostMapping
    ResponseEntity<VatRuleGetResource> post(@RequestBody VatRulePostResource vatRulePostResource);

    @PatchMapping("/{vatRuleId}")
    ResponseEntity<VatRuleGetResource> patchById(@PathVariable String vatRuleId, @RequestBody VatRulePatchResource vatRulePatchResource);

    @DeleteMapping("/{vatRuleId}")
    ResponseEntity<Void> deleteById(@PathVariable String vatRuleId);
}
