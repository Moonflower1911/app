package com.smsmode.invoice.resource.booking.get;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateGetResource {
    private BigDecimal unitPriceExclTax;
    private BigDecimal unitPriceInclTax;
    private BigDecimal quantity;
    private BigDecimal vatPercentage;
    private BigDecimal vatAmount;
    private BigDecimal amountExclTax;
    private BigDecimal amountInclTax;
}
