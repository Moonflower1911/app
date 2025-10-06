package com.smsmode.invoice.resource.payment;


import com.smsmode.invoice.embeddable.RefEmbeddable;
import com.smsmode.invoice.enumeration.PaymentMethodEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentPatchResource {
    private BigDecimal amount;
    private PaymentMethodEnum method;
    private String reference;
    private String note;
    private RefEmbeddable booking;
}
