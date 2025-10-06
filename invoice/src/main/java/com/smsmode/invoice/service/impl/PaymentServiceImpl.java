package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.PaymentDaoService;
import com.smsmode.invoice.dao.specification.PaymentSpecification;
import com.smsmode.invoice.mapper.PaymentMapper;
import com.smsmode.invoice.model.PaymentModel;
import com.smsmode.invoice.resource.payment.PaymentGetResource;
import com.smsmode.invoice.resource.payment.PaymentPatchResource;
import com.smsmode.invoice.resource.payment.PaymentPostResource;
import com.smsmode.invoice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentDaoService paymentDaoService;

    @Override
    @Transactional
    public ResponseEntity<PaymentGetResource> create(PaymentPostResource paymentPostResource) {
        log.debug("Creating new payment with reference {}", paymentPostResource.getReference());

        PaymentModel paymentModel = paymentMapper.postResourceToModel(paymentPostResource);
        paymentModel = paymentDaoService.save(paymentModel);

        PaymentGetResource paymentGetResource = paymentMapper.modelToGetResource(paymentModel);

        return ResponseEntity.created(URI.create("")).body(paymentGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<PaymentGetResource> retrieveById(String paymentId) {
        PaymentModel paymentModel = paymentDaoService.findOneBy(PaymentSpecification.withIdEqual(paymentId));
        PaymentGetResource paymentGetResource = paymentMapper.modelToGetResource(paymentModel);
        return ResponseEntity.ok(paymentGetResource);
    }

    @Override
    public ResponseEntity<Page<PaymentGetResource>> retrieveAllByPage(String search, Pageable pageable) {
        Specification<PaymentModel> specification = Specification.where(
                PaymentSpecification.withReferenceLike(search));

        Page<PaymentModel> payments = paymentDaoService.findAllBy(specification, pageable);

        return ResponseEntity.ok(payments.map(paymentMapper::modelToGetResource));
    }


    @Override
    @Transactional
    public ResponseEntity<PaymentGetResource> updateById(String paymentId, PaymentPatchResource paymentPatchResource) {
        PaymentModel paymentModel = paymentDaoService.findOneBy(PaymentSpecification.withIdEqual(paymentId));
        paymentModel = paymentMapper.patchResourceToModel(paymentPatchResource, paymentModel);
        paymentModel = paymentDaoService.save(paymentModel);
        PaymentGetResource paymentGetResource = paymentMapper.modelToGetResource(paymentModel);
        return ResponseEntity.ok(paymentGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeById(String paymentId) {
        PaymentModel paymentModel = paymentDaoService.findOneBy(PaymentSpecification.withIdEqual(paymentId));
        paymentDaoService.delete(paymentModel);
        return ResponseEntity.noContent().build();
    }


}
