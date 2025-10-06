/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.validator.impl;

import com.smsmode.pricing.dao.service.ChargeDaoService;
import com.smsmode.pricing.dao.specification.ChargeSpecification;
import com.smsmode.pricing.validator.UniqueChargeCode;
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
public class UniqueChargeCodeImpl implements ConstraintValidator<UniqueChargeCode, String> {
    private final ChargeDaoService chargeDaoService;

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(code)) {
            return true;
        } else {
            return !chargeDaoService.existsBy(ChargeSpecification.withCodeEqual(code));
        }
    }
}
