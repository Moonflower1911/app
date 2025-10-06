/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.validator;

import com.smsmode.pricing.validator.impl.UniqueCancellationPolicyCodeImpl;
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
@Constraint(validatedBy = UniqueCancellationPolicyCodeImpl.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCancellationPolicyCode {
    String message() default "Code must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
