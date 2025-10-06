package com.smsmode.invoice.resource.invoice.post;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemPostResource {
    @NotBlank
    private String description;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitPrice;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal taxPercentage;
}
