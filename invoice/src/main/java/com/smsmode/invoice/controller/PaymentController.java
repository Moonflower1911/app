package com.smsmode.invoice.controller;

import com.smsmode.invoice.resource.payment.PaymentGetResource;
import com.smsmode.invoice.resource.payment.PaymentPatchResource;
import com.smsmode.invoice.resource.payment.PaymentPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payments")
public interface PaymentController {

    @PostMapping
    ResponseEntity<PaymentGetResource> post(@RequestBody @Valid PaymentPostResource paymentPostResource);

    @GetMapping("{paymentId}")
    ResponseEntity<PaymentGetResource> getById(@PathVariable("paymentId") String paymentId);

    @GetMapping
    ResponseEntity<Page<PaymentGetResource>> getAllByPage(
            @RequestParam(required = false) String search,
            Pageable pageable
    );

    @PatchMapping("{paymentId}")
    ResponseEntity<PaymentGetResource> patchById(@PathVariable String paymentId,
                                                 @Valid @RequestBody PaymentPatchResource paymentPatchResource);

    @DeleteMapping("{paymentId}")
    ResponseEntity<Void> deleteById(@PathVariable String paymentId);

}
