/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.service;

import com.smsmode.invoice.resource.vatrule.VatRuleGetResource;
import com.smsmode.invoice.resource.vatrule.VatRulePatchResource;
import com.smsmode.invoice.resource.vatrule.VatRulePostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
public interface VatRuleService {
    ResponseEntity<Page<VatRuleGetResource>> retrieveAll(String search, Pageable pageable);

    ResponseEntity<VatRuleGetResource> create(VatRulePostResource vatRulePostResource);

    ResponseEntity<VatRuleGetResource> updateById(String vatRuleId, VatRulePatchResource vatRulePatchResource);

    ResponseEntity<Void> removeById(String vatRuleId);

}
