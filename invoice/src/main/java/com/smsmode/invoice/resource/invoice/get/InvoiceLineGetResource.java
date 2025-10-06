package com.smsmode.invoice.resource.invoice.get;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceLineGetResource {
    private String id;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal taxPercentage;
    private Integer quantity;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
}
