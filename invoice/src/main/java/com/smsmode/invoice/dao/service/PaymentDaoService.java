package com.smsmode.invoice.dao.service;

import com.smsmode.invoice.model.PaymentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PaymentDaoService {
    PaymentModel save(PaymentModel paymentModel);

    PaymentModel findOneBy(Specification<PaymentModel> specification);

    void delete(PaymentModel paymentModel);

    Page<PaymentModel> findAllBy(Specification<PaymentModel> specification, Pageable pageable);

}
