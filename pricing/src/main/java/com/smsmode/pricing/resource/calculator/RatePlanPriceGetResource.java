/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.calculator;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Sep 2025</p>
 */

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RatePlanPriceGetResource {

    private String id;
    private String name;
    private String description;
    private BigDecimal avgRateNight;
    private BigDecimal totalBookingRate;
    private List<DatedRateGetResource> dailyRates;

}
