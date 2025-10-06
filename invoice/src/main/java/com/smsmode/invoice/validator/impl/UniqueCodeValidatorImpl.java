package com.smsmode.invoice.validator.impl;

import com.smsmode.invoice.dao.service.PostingAccountsDaoService;
import com.smsmode.invoice.dao.specification.PostingAccountsSpecification;
import com.smsmode.invoice.validator.UniqueCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UniqueCodeValidatorImpl implements ConstraintValidator<UniqueCode, String> {

    private final PostingAccountsDaoService postingAccountsDaoService;

    @Autowired
    public UniqueCodeValidatorImpl(PostingAccountsDaoService postingAccountsDaoService) {
        this.postingAccountsDaoService = postingAccountsDaoService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) {
            return true;
        } else {
            return !postingAccountsDaoService.existsBy(PostingAccountsSpecification.withCodeEqual(value));
        }
    }
}