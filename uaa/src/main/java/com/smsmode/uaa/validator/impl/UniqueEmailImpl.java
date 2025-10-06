/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.validator.impl;

import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.UserSpecification;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.util.PathVariableHelper;
import com.smsmode.uaa.validator.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

/**
 * Custom validation implementation for ensuring the uniqueness of an email address. This class
 * implements the {@link ConstraintValidator} interface to perform the email validation logic,
 * specifically ensuring that the email provided is not already associated with an existing user in
 * the system.
 *
 * <p>The validator checks if the given email address already exists in the database by using the
 * {@link UserDaoService}. If the email is already in use, the validation fails, and an error
 * message is triggered.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 17 Oct 2024
 */
@RequiredArgsConstructor
public class UniqueEmailImpl implements ConstraintValidator<UniqueEmail, String> {

    private final UserDaoService userDaoService;

    /**
     * Validates that the provided email is unique and not assigned to an existing user.
     *
     * <p>This method checks if the email is either empty or not already present in the database. If
     * the email exists in the system, the validation fails and a constraint violation is triggered.
     *
     * @param email   The email to be validated.
     * @param context The validation context, allowing access to additional validation information.
     * @return {@code true} if the email is either empty or does not exist in the database, {@code
     * false} otherwise.
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // if user email is empty
        if (ObjectUtils.isEmpty(email)) {
            return true;
        } else {
            // user creation
            if (Boolean.FALSE.equals(userDaoService.existsBy(UserSpecification.withEmail(email)))) {
                return true;
            }

            // user update
            String userId = PathVariableHelper.getPathVariable("userId");
            UserModel userModel = userDaoService.findOneBy(UserSpecification.withUuid(userId));
            return userModel != null && userModel.getEmail().equals(email);
        }
    }
}
