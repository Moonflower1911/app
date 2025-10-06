/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.controller.impl;

import com.smsmode.uaa.controller.AuthController;
import com.smsmode.uaa.resource.auth.*;
import com.smsmode.uaa.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of {@link AuthController} that handles user authentication-related operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Oct 2024
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationService authenticationService;

    /**
     * {@inheritDoc}
     */
    public ResponseEntity<TokenGetResource> postCredentials(
            UserCredentialsPostResource userCredentialsPostResource) {
        return authenticationService.authenticate(userCredentialsPostResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> postEmailToInitiateForgotPassword(
            UserForgotPasswordPostResource userForgotPasswordPostResource) {
        return authenticationService.forgotPassword(userForgotPasswordPostResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> resetPassword(
            UserResetPasswordPostResource userResetPasswordPostResource) {
        return authenticationService.resetPassword(userResetPasswordPostResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> validateAccount(
            UserValidateAccountPostResource userValidateAccountPostResource) {
        return authenticationService.validateAccount(userValidateAccountPostResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Void> validateToken(
            TokenValidationPostResource tokenValidationPostResource) {
        return authenticationService.validateToken(tokenValidationPostResource);
    }

    @Override
    public ResponseEntity<TokenGetResource> refreshToken(
            RefreshTokenPostResource refreshTokenPostResource) {
        return authenticationService.refreshToken(refreshTokenPostResource);
    }

}
