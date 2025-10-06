/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.validator;

import com.smsmode.pricing.validator.impl.UniqueChargeCodeImpl;
import com.smsmode.pricing.validator.impl.UnitRatePlanCodeImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 03 Sep 2025</p>
 */
@Documented
@Constraint(validatedBy = UniqueChargeCodeImpl.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueChargeCode {
    String message() default "Code must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
