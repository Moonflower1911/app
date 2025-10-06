package com.smsmode.pricing.resource.old.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NightRateGetResource {
    private String date;
    private BigDecimal rate;
}
