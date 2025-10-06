package com.smsmode.invoice.model;

import com.smsmode.invoice.embeddable.RefEmbeddable;
import com.smsmode.invoice.enumeration.PaymentMethodEnum;
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
@Table(name = "X_PAYMENT")
public class PaymentModel extends AbstractBaseModel {

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "METHOD", nullable = false)
    private PaymentMethodEnum method;

    @Column(name = "REFERENCE", length = 100, nullable = false)
    private String reference;

    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "BOOKING_ID", nullable = false))
    })
    private RefEmbeddable booking;
}

