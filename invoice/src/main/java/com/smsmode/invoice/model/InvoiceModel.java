package com.smsmode.invoice.model;

import com.smsmode.invoice.embeddable.ClientRefEmbeddable;
import com.smsmode.invoice.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "X_INVOICE")
public class InvoiceModel extends AbstractBaseModel {

    @Column(name = "REFERENCE", nullable = false, unique = true)
    private String reference;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "PROFORMA", nullable = false)
    private boolean proforma;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "CLIENT_ID")),
            @AttributeOverride(name = "name", column = @Column(name = "CLIENT_NAME"))
    })
    private ClientRefEmbeddable clientRef;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "TAX_AMOUNT", nullable = false)
    private BigDecimal taxAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "INVOICE_ID")
    private List<InvoiceItemModel> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "INVOICE_ID")
    private List<PaymentModel> payments = new ArrayList<>();


}
