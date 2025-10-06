package com.smsmode.invoice.controller.impl;

import com.smsmode.invoice.controller.PaymentController;
import com.smsmode.invoice.resource.payment.PaymentGetResource;
import com.smsmode.invoice.resource.payment.PaymentPatchResource;
import com.smsmode.invoice.resource.payment.PaymentPostResource;
import com.smsmode.invoice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<PaymentGetResource> post(PaymentPostResource paymentPostResource) {
        return paymentService.create(paymentPostResource);
    }

    @Override
    public ResponseEntity<PaymentGetResource> getById(String paymentId) {
        return paymentService.retrieveById(paymentId);
    }

    @Override
    public ResponseEntity<Page<PaymentGetResource>> getAllByPage(String search, Pageable pageable) {
        return paymentService.retrieveAllByPage(search, pageable);
    }


    @Override
    public ResponseEntity<PaymentGetResource> patchById(String paymentId, PaymentPatchResource paymentPatchResource) {
        return paymentService.updateById(paymentId, paymentPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String paymentId) {
        return paymentService.removeById(paymentId);
    }
}
