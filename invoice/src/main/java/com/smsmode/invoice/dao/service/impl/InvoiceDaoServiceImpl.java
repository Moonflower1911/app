package com.smsmode.invoice.dao.service.impl;

import com.smsmode.invoice.dao.repository.InvoiceRepository;
import com.smsmode.invoice.dao.service.InvoiceDaoService;
import com.smsmode.invoice.exception.ResourceNotFoundException;
import com.smsmode.invoice.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.invoice.model.InvoiceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceDaoServiceImpl implements InvoiceDaoService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Page<InvoiceModel> findAllBy(Specification<InvoiceModel> specification, Pageable pageable) {
        return invoiceRepository.findAll(specification, pageable);
    }

    @Override
    public InvoiceModel findOneBy(Specification<InvoiceModel> specification) {
        return invoiceRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any invoice with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.INVOICE_NOT_FOUND,
                            "No invoice found with the specified criteria");
                });
    }

}
