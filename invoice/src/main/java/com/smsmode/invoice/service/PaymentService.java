package com.smsmode.invoice.service;

import com.smsmode.invoice.resource.payment.PaymentGetResource;
import com.smsmode.invoice.resource.payment.PaymentPatchResource;
import com.smsmode.invoice.resource.payment.PaymentPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    ResponseEntity<PaymentGetResource> create(PaymentPostResource paymentPostResource);

    ResponseEntity<PaymentGetResource> retrieveById(String paymentId);

    ResponseEntity<Page<PaymentGetResource>> retrieveAllByPage(String search, Pageable pageable);

    ResponseEntity<PaymentGetResource> updateById(String paymentId, PaymentPatchResource paymentPatchResource);

    ResponseEntity<Void> removeById(String paymentId);
}
