package com.smsmode.invoice.resource.payment;

import com.smsmode.invoice.embeddable.RefEmbeddable;
import com.smsmode.invoice.enumeration.PaymentMethodEnum;
import com.smsmode.invoice.resource.common.AuditGetResource;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentGetResource {
    private String id;
    private BigDecimal amount;
    private PaymentMethodEnum method;
    private String reference;
    private String note;
    private RefEmbeddable booking;
    private AuditGetResource audit;
}
