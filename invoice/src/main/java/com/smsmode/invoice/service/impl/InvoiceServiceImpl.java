package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.InvoiceDaoService;
import com.smsmode.invoice.dao.specification.InvoiceSpecification;
import com.smsmode.invoice.mapper.InvoiceMapper;
import com.smsmode.invoice.model.InvoiceModel;
import com.smsmode.invoice.pdf.InvoicePdfGenerator;
import com.smsmode.invoice.resource.booking.get.BookingGetResource;
import com.smsmode.invoice.resource.invoice.get.InvoiceItemGetResource;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import com.smsmode.invoice.service.InvoiceService;
import com.smsmode.invoice.service.feign.BookingFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceDaoService invoiceDaoService;
    private final InvoiceMapper invoiceMapper;
    private final InvoicePdfGenerator pdfGenerator;
    private final BookingFeignService bookingFeignService;

    @Override
    public ResponseEntity<Page<InvoiceItemGetResource>> retrieveAllByPage(String search, Pageable pageable) {
        Specification<InvoiceModel> specification = InvoiceSpecification.withReferenceLike(search)
                .or(InvoiceSpecification.withStatusLike(search))
                .or(InvoiceSpecification.withClientNameLike(search));
        Page<InvoiceModel> invoices = invoiceDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(invoices.map(invoiceMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<InvoiceItemGetResource> retrieveById(String invoiceId) {
        InvoiceModel invoice = invoiceDaoService.findOneBy(InvoiceSpecification.withIdEqual(invoiceId));
        return ResponseEntity.ok(invoiceMapper.modelToGetResource(invoice));
    }

    @Override
    public byte[] generateInvoice(InvoicePdfPostResource invoicePdfPostResource) {
        return pdfGenerator.generate(invoicePdfPostResource);
    }

    @Override
    public byte[] generateInvoiceByBookingId(String bookingId) {
        BookingGetResource booking = bookingFeignService
                .getById(bookingId)
                .getBody();

        if (booking == null) {
            throw new IllegalArgumentException("Booking not found for ID: " + bookingId);
        }

        InvoicePdfPostResource invoicePdfPostResource =
                invoiceMapper.bookingToInvoicePdfPostResource(booking);

        return pdfGenerator.generate(invoicePdfPostResource);
    }

}
