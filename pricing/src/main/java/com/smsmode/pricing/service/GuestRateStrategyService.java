/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyGetResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyItemGetResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyPatchResource;
import com.smsmode.pricing.resource.guestratestrategy.GuestRateStrategyPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
public interface GuestRateStrategyService {

    ResponseEntity<Page<GuestRateStrategyItemGetResource>> retrieveAll(String search, Boolean enabled, Pageable pageable);

    ResponseEntity<GuestRateStrategyGetResource> retrieveById(String guestRateStrategyId);

    ResponseEntity<GuestRateStrategyGetResource> create(GuestRateStrategyPostResource guestRateStrategyPostResource);

    ResponseEntity<GuestRateStrategyGetResource> update(String guestRateStrategyId, GuestRateStrategyPatchResource guestRateStrategyPatchResource);


}
