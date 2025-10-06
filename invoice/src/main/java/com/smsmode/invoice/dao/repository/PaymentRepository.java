package com.smsmode.invoice.dao.repository;

import com.smsmode.invoice.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends JpaRepository<PaymentModel, String>, JpaSpecificationExecutor<PaymentModel> {
}
