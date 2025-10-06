package com.smsmode.uaa.validator;

import com.smsmode.uaa.validator.impl.UniqueEmailImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation for ensuring the uniqueness of an email address. This annotation can
 * be applied to a field to ensure that the email provided is not already associated with an
 * existing account. It works in conjunction with the {@link UniqueEmailImpl} validator
 * implementation.
 *
 * <p>This annotation is typically used in user registration or account creation scenarios to
 * prevent duplicate email entries in the system.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Oct 2024
 */
@Documented
@Constraint(validatedBy = UniqueEmailImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    /**
     * The default error message when the email address is already in use. This message will be used
     * if no specific message is provided during validation.
     *
     * @return the default error message.
     */
    String message() default "An account with this email already exists.";

    /**
     * Allows grouping of constraints. This is used for validation groups, enabling different
     * validation rules for different use cases.
     *
     * @return an array of validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Carries additional metadata about the annotation. This can be used for reporting or logging
     * purposes.
     *
     * @return an array of payload classes.
     */
    Class<? extends Payload>[] payload() default {};
}
