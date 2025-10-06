/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.charge.ChargeItemGetResource;
import com.smsmode.pricing.resource.charge.ChargePatchResource;
import com.smsmode.pricing.resource.charge.ChargePostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
public interface ChargeService {
    ResponseEntity<Page<ChargeItemGetResource>> retrieveAll(String search, Boolean isExtra, Boolean isPackage, Boolean enabled, Pageable pageable);

    ResponseEntity<ChargeItemGetResource> retrieveById(String chargeId);

    ResponseEntity<ChargeItemGetResource> create(ChargePostResource chargePostResource);

    ResponseEntity<ChargeItemGetResource> updateById(String chargeId, ChargePatchResource chargePatchResource);

    ResponseEntity<Void> deleteById(String chargeId);
}
