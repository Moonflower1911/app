package com.smsmode.pricing.validator.old;

import com.smsmode.pricing.validator.old.impl.RateTableDatesValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RateTableDatesValidatorImpl.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRateTableDates {
    String message() default "Rate table dates overlap with existing rate table of same type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}