/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.controller;

import com.smsmode.uaa.resource.auth.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller interface for handling user authentication-related operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Oct 2024
 */
public interface AuthController {

    /**
     * Handles user login and returns an authentication token.
     *
     * @param userCredentialsPostResource The resource containing user login credentials.
     * @return ResponseEntity containing the authentication token if login is successful.
     */
    @PostMapping("/login")
    ResponseEntity<TokenGetResource> postCredentials(
            @RequestBody @Valid UserCredentialsPostResource userCredentialsPostResource);

    /**
     * Initiates the process of resetting a user's forgotten password.
     *
     * @param userForgotPasswordPostResource The resource containing the user's email for password
     *                                       reset.
     * @return ResponseEntity indicating the success or failure of the password reset initiation.
     */
    @PostMapping("/forgot-password")
    ResponseEntity<Void> postEmailToInitiateForgotPassword(
            @Valid @RequestBody UserForgotPasswordPostResource userForgotPasswordPostResource);

    /**
     * Handles the reset of a user's password.
     *
     * @param userResetPasswordPostResource The resource containing the new password and reset token.
     * @return ResponseEntity indicating the success or failure of the password reset.
     */
    @PostMapping("/reset-password")
    ResponseEntity<Void> resetPassword(
            @Valid @RequestBody UserResetPasswordPostResource userResetPasswordPostResource);

    /**
     * Handles the user account activation.
     *
     * @param userValidateAccountPostResource The resource containing activation key.
     * @return ResponseEntity indicating the success or failure of activation account process.
     */
    @PostMapping("/validate-account")
    ResponseEntity<Void> validateAccount(
            @Valid @RequestBody UserValidateAccountPostResource userValidateAccountPostResource);

    /**
     * Handles token validation.
     *
     * @param tokenValidationPostResource the request body containing the token to be validated.
     * @return a {@link ResponseEntity} with no content if the token is valid. If the token is
     * invalid, an appropriate HTTP error status will be returned.
     */
    @PostMapping("/validate-token")
    ResponseEntity<Void> validateToken(
            @RequestBody TokenValidationPostResource tokenValidationPostResource);

    @PostMapping("/refresh-token")
    ResponseEntity<TokenGetResource> refreshToken(@Valid @RequestBody RefreshTokenPostResource refreshTokenPostResource);

}
