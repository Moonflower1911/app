package com.smsmode.invoice.dao.service.impl;

import com.smsmode.invoice.dao.repository.PaymentRepository;
import com.smsmode.invoice.dao.service.PaymentDaoService;
import com.smsmode.invoice.exception.ResourceNotFoundException;
import com.smsmode.invoice.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.invoice.model.PaymentModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentDaoServiceImpl implements PaymentDaoService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentModel save(PaymentModel paymentModel) {
        return paymentRepository.save(paymentModel);
    }

    @Override
    public PaymentModel findOneBy(Specification<PaymentModel> specification) {
        return paymentRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any payment with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.PAYMENT_NOT_FOUND,
                            "No payment found with the specified criteria");
                });
    }

    @Override
    public void delete(PaymentModel paymentModel) {
        paymentRepository.delete(paymentModel);
    }

    @Override
    public Page<PaymentModel> findAllBy(Specification<PaymentModel> specification, Pageable pageable) {
        return paymentRepository.findAll(specification, pageable);
    }
}
