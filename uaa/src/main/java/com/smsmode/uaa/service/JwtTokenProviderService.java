/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.smsmode.uaa.model.UserModel;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

/**
 * Service interface for generating and handling JWT (JSON Web Token) authentication tokens.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Oct 2024
 */
public interface JwtTokenProviderService {

    /**
     * Retrieve expiration date from the JWT token.
     *
     * @param token User's JWT token.
     * @return Token's expiration date.
     */
    Date getExpirationDateFromToken(String token);

    /**
     * Retrieve any claim from the JWT token.
     *
     * @param token          User's JWT token.
     * @param claimsResolver Function from Claims class.
     * @param <T>            Generic type of the claim.
     * @return The value of the claim.
     */
    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    /**
     * Retrieve all claims from the JWT token.
     *
     * @param token User's JWT token.
     * @return All claims from the token.
     */
    Claims getClaimsFromToken(String token);

    String getClaimFromTokenEvenIfExpired(String token, String claimName);

    DecodedJWT getClaimsFromTokenEvenIfExpired(String token);

    /**
     * Generate a JWT token from an authentication instance and a user instance.
     *
     * @param user Instance of {@code UserModel} representing a user stored in the database.
     * @return Generated JWT token.
     */
    String generateToken(UserModel user);

    /**
     * Validates an authentication token by checking if it is not expired and if the associated user
     * exists and is active.
     *
     * @param token The token to validate, which is expected to be a JWT .
     * @return {@code true} if the token is valid (not expired) and the user exists, is enabled, and
     * is active; {@code false} if the token is expired or if any error occurs during validation.
     */
    boolean validateToken(String token);
}
