/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service;

import com.smsmode.pricing.resource.calculator.BookingPostResource;
import com.smsmode.pricing.resource.calculator.RatePlanPriceGetResource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Sep 2025</p>
 */
public interface CalculatorService {
    ResponseEntity<Map<String, List<RatePlanPriceGetResource>>> calculateBooking(BookingPostResource bookingPostResource);
}
