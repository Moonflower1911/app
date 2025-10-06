package com.smsmode.invoice.controller.impl;

import com.smsmode.invoice.controller.InvoiceController;
import com.smsmode.invoice.resource.invoice.get.InvoiceItemGetResource;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import com.smsmode.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InvoiceControllerImpl implements InvoiceController {

    private final InvoiceService invoiceService;

    @Override
    public ResponseEntity<Page<InvoiceItemGetResource>> getAllInvoices(String search, Pageable pageable) {
        return invoiceService.retrieveAllByPage(search, pageable);
    }

    @Override
    public ResponseEntity<InvoiceItemGetResource> getInvoiceById(String invoiceId) {
        return invoiceService.retrieveById(invoiceId);
    }

    @Override
    public ResponseEntity<byte[]> generateInvoicePdf(InvoicePdfPostResource invoicePdfPostResource) {
        byte[] pdfBytes = invoiceService.generateInvoice(invoicePdfPostResource);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @Override
    public ResponseEntity<byte[]> generateInvoicePdfByBookingId(String bookingId) {
        byte[] pdfBytes = invoiceService.generateInvoiceByBookingId(bookingId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
