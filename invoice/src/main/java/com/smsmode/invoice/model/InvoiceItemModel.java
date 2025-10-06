package com.smsmode.invoice.model;

import com.smsmode.invoice.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "X_INVOICE_ITEM")
public class InvoiceItemModel extends AbstractBaseModel {

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "UNIT_PRICE", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "TAX_PERCENTAGE", nullable = false)
    private BigDecimal taxPercentage;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "TAX_AMOUNT", nullable = false)
    private BigDecimal taxAmount;
}
