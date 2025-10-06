/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.validator.impl;

import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.dao.specification.RatePlanSpecification;
import com.smsmode.pricing.validator.UniqueRatePlanCode;
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
public class UnitRatePlanCodeImpl implements ConstraintValidator<UniqueRatePlanCode, String> {
    private final RatePlanDaoService ratePlanDaoService;

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(code)) {
            return true;
        } else {
            return !ratePlanDaoService.existsBy(RatePlanSpecification.withCodeEqual(code));
        }
    }
}
