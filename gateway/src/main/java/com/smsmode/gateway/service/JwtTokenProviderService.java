package com.smsmode.gateway.service;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Service interface for managing and validating JWT tokens.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
public interface JwtTokenProviderService {

  /**
   * Retrieves a specified claim from the JWT token.
   *
   * @param token The JWT token.
   * @param claimName The name of the claim to retrieve.
   * @return The value of the specified claim, or {@code null} if the claim is not found.
   */
  String getClaimFromToken(String token, String claimName);

  /**
   * Retrieve all claims from the JWT token.
   *
   * @param token User's JWT token.
   * @return All claims from the token.
   */
  DecodedJWT getClaimsFromToken(String token);

  /**
   * Checks if the provided JWT token has expired.
   *
   * @param token The JWT token.
   * @return {@code true} if the token is expired, {@code false} otherwise.
   */
  boolean isTokenExpired(String token);
}
