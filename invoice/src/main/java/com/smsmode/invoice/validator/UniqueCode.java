package com.smsmode.invoice.validator;

import com.smsmode.invoice.validator.impl.UniqueCodeValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCodeValidatorImpl.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCode {
    String message() default "Code already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}