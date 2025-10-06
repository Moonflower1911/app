package com.smsmode.invoice.dao.repository;

import com.smsmode.invoice.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, String>, JpaSpecificationExecutor<InvoiceModel> {
}
