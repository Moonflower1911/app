/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.validator.impl;

import com.smsmode.pricing.dao.service.CancellationPolicyDaoService;
import com.smsmode.pricing.dao.specification.CancellationPolicySpecification;
import com.smsmode.pricing.validator.UniqueCancellationPolicyCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 03 Sep 2025</p>
 */
@Slf4j
@RequiredArgsConstructor
public class UniqueCancellationPolicyCodeImpl implements ConstraintValidator<UniqueCancellationPolicyCode, String> {
    private final CancellationPolicyDaoService cancellationPolicyDaoService;

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(code)) {
            return true;
        } else {
            return !cancellationPolicyDaoService.existsBy(CancellationPolicySpecification.withCodeEqual(code));
        }
    }
}
