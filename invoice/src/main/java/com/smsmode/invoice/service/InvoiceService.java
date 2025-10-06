package com.smsmode.invoice.service;

import com.smsmode.invoice.resource.invoice.get.InvoiceItemGetResource;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<Page<InvoiceItemGetResource>> retrieveAllByPage(String search, Pageable pageable);

    ResponseEntity<InvoiceItemGetResource> retrieveById(String invoiceId);

    byte[] generateInvoice(InvoicePdfPostResource invoicePdfPostResource);

    byte[] generateInvoiceByBookingId(String bookingId);
}
