/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;

import com.smsmode.uaa.exception.AuthorizationForbiddenException;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.resource.auth.*;
import org.springframework.http.ResponseEntity;

/**
 * Service interface providing authentication functionalities.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Oct 2024
 */
public interface AuthenticationService {

    /**
     * Authenticate a user to return a token in case credentials are correct otherwise throw several
     * exceptions if
     *
     * <ul>
     *   <li>Too many bad login attempts
     *   <li>Bad credentials
     *   <li>Account not validated
     *   <li>Account disabled
     * </ul>
     *
     * @param userCredentialsPostResource The resource containing user login credentials.
     * @return ResponseEntity containing the authentication token if login is successful.
     */
    ResponseEntity<TokenGetResource> authenticate(
            UserCredentialsPostResource userCredentialsPostResource);

    /*
    /**
       * Initiates the forgot password process for a user. This method attempts to find a user by the
       * provided email address, generates a reset key, saves the updated user information, and sends a
       * reset password email. If no user is found with the given email, no response is returned to the
       * user.
       *
       * @param userForgotPasswordPostResource the resource containing the user's email address for
       *                                       which the password reset is to be initiated.
       * @return a ResponseEntity with no content (HTTP 204 status).
       */
    ResponseEntity<Void> forgotPassword(
            UserForgotPasswordPostResource userForgotPasswordPostResource);

    /**
     * Resets the password for a user based on the provided reset key. If the reset key is expired, a
     * new reset key is generated and sent to the user's email. If the reset key is valid, the user's
     * password is updated and a confirmation email is sent.
     *
     * @param userResetPasswordPostResource the resource containing the reset key and the new password
     * @return a response entity with no content if the password is successfully reset
     * @throws ResourceNotFoundException       if the reset key is invalid or not found
     * @throws AuthorizationForbiddenException if the reset key is expired
     */
    ResponseEntity<Void> resetPassword(UserResetPasswordPostResource userResetPasswordPostResource);

    /**
     * Validate a user account using the provided activation key
     *
     * @param userValidateAccountPostResource the resource containing the activation key to validate
     *                                        the account
     * @return ResponseEntity with a status code of 200 if the account is successfully validated.
     * @throws ResourceNotFoundException       a status code of 404 if the activation key is invalid or not
     *                                         found.
     * @throws AuthorizationForbiddenException a status code of 403 if the activation key has expired.
     */
    ResponseEntity<Void> validateAccount(
            UserValidateAccountPostResource userValidateAccountPostResource);

    /**
     * Validates a provided authentication token. If the token is invalid, it throws an exception
     * indicating that the token is not valid.
     *
     * @param tokenValidationPostResource The resource containing the token to be validated.
     * @return A {@link ResponseEntity} with no content (HTTP status 204) if the token is valid.
     * @throws AuthorizationForbiddenException If the token is not valid, an {@link
     *                                         AuthorizationForbiddenException} is thrown with a specific error message.
     */
    ResponseEntity<Void> validateToken(TokenValidationPostResource tokenValidationPostResource);

    ResponseEntity<TokenGetResource> refreshToken(RefreshTokenPostResource refreshTokenPostResource);

}
