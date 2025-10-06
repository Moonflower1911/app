package com.smsmode.invoice.controller;

import com.smsmode.invoice.resource.invoice.get.InvoiceItemGetResource;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/invoices")
public interface InvoiceController {

    @GetMapping
    ResponseEntity<Page<InvoiceItemGetResource>> getAllInvoices(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable);

    @GetMapping("/{invoiceId}")
    ResponseEntity<InvoiceItemGetResource> getInvoiceById(@PathVariable("invoiceId") String invoiceId);

    @PostMapping(
            value = "/generate-pdf",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    ResponseEntity<byte[]> generateInvoicePdf(
            @Valid @RequestBody InvoicePdfPostResource invoicePdfPostResource);

    @PostMapping(
            value = "/generate-pdf/{bookingId}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    ResponseEntity<byte[]> generateInvoicePdfByBookingId(
            @PathVariable String bookingId
    );

}
