package com.smsmode.invoice.dao.service;

import com.smsmode.invoice.model.InvoiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface InvoiceDaoService {
    Page<InvoiceModel> findAllBy(Specification<InvoiceModel> specification, Pageable pageable);

    InvoiceModel findOneBy(Specification<InvoiceModel> specification);

}
