package com.smsmode.invoice.resource.invoice.get;

import com.smsmode.invoice.resource.common.AuditGetResource;
import com.smsmode.invoice.resource.payment.PaymentGetResource;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceItemGetResource {

    private String id;
    private String reference;
    private LocalDate date;
    private boolean proforma;
    private String status;
    private String clientId;
    private String clientName;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private List<InvoiceLineGetResource> items;
    private List<PaymentGetResource> payments;
    private AuditGetResource audit;
}