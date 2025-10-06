package com.smsmode.gateway.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smsmode.gateway.service.JwtTokenProviderService;
import com.smsmode.gateway.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Implementation of the {@link JwtTokenProviderService} interface for managing and validating JWT
 * tokens.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
@Service
public class JwtTokenProviderServiceImpl implements JwtTokenProviderService {

  /** {@inheritDoc} */
  @Override
  public String getClaimFromToken(String token, String claimName) {
    if (this.getClaimsFromToken(token).getClaim(claimName).isMissing()) {
      return null;
    } else {
      return this.getClaimsFromToken(token).getClaim(claimName).asString();
    }
  }

  /** {@inheritDoc} */
  @Override
  public DecodedJWT getClaimsFromToken(String token) {
    token = token.replace(SecurityUtils.TOKEN_TYPE, "").trim();
    return JWT.decode(token);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isTokenExpired(String token) {
    return this.getClaimsFromToken(token).getExpiresAt().before(new Date());
  }
}
