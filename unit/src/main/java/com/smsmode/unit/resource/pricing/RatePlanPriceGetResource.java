package com.smsmode.unit.resource.pricing;

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
