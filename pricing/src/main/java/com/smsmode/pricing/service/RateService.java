/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.rate.RatePostResource;
import com.smsmode.pricing.resource.rate.UnitRateGetResource;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 04 Sep 2025</p>
 */
public interface RateService {

    ResponseEntity<List<UnitRateGetResource>> retrieveAll(String ratePlanId, LocalDate startDate, LocalDate endDate);

    ResponseEntity<List<UnitRateGetResource>> create(RatePostResource ratePostResource);

}
