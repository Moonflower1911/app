/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.UserSpecification;
import com.smsmode.uaa.model.RoleModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.service.JwtTokenProviderService;
import com.smsmode.uaa.util.DefaultClock;
import com.smsmode.uaa.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.smsmode.uaa.util.SecurityUtil.*;


/**
 * Implementation of the {@link JwtTokenProviderService} interface, providing functionality for
 * handling JWT (JSON Web Token) authentication tokens.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Oct 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Getter
@Setter
public class JwtTokenProviderServiceImpl implements JwtTokenProviderService {

    public static final Clock clock = DefaultClock.INSTANCE;
    private final UserDaoService credentialDaoService;

    @Value("${uaa.security.access-token.expiration}")
    public Long expiration;

    @Value("${uaa.security.access-token.key}")
    private String secret;

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getExpirationDateFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Claims getClaimsFromToken(String token) {
        token = token.replace(SecurityUtil.TOKEN_TYPE, "").trim();
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public String getClaimFromTokenEvenIfExpired(String token, String claimName) {
        if (this.getClaimsFromTokenEvenIfExpired(token).getClaim(claimName).isMissing()) {
            return null;
        } else {
            return this.getClaimsFromTokenEvenIfExpired(token).getClaim(claimName).asString();
        }
    }

    @Override
    public DecodedJWT getClaimsFromTokenEvenIfExpired(String token) {
        token = token.replace(SecurityUtil.TOKEN_TYPE, "").trim();
        return JWT.decode(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateToken(UserModel login) {
        String authorities =
                login.getRoles().stream().map(RoleModel::getAuthority).collect(Collectors.joining(","));
        String username = login.getEmail();
        log.debug("User: <subject: {} {}> -> authorities: {}", login.getFullName(), username, authorities);
        final Date createdDate = clock.now();
        LocalDate accountEndDate = null;
        if (login.getAccount() != null) {
            accountEndDate = login.getAccount().getEndDate();
        }
        Date expirationDate = calculateExpirationDate(createdDate, accountEndDate);
        Map<String, Object> claims = new HashMap<>();
        claims.put(IDENTIFIER_KEY, login.getId());
        claims.put(AUTHORITIES_KEY, authorities);
        claims.put(USERNAME_KEY, username);


        return Jwts.builder()
                .subject(login.getFullName())
                .claims(claims)
                .expiration(expirationDate)
                .issuedAt(createdDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateToken(String token) {
        token = token.replace(SecurityUtil.TOKEN_TYPE, "").trim();
        try {
            if (!isTokenExpired(token)) {
                Claims claims = getClaimsFromToken(token);
                String userId = claims.get(IDENTIFIER_KEY).toString();
                return credentialDaoService.existsBy(
                        UserSpecification.withUuid(userId)
                                .and(UserSpecification.withEnabled(true))
                                .and(UserSpecification.withActive(true)));
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calculates the expiration date of a token based on the provided created date and expiration
     * duration.
     *
     * @param createdDate The date when the token was created.
     * @return The calculated expiration date.
     */
    public Date calculateExpirationDate(Date createdDate, LocalDate accountEndDate) {
        Date defaultExpiration = new Date(createdDate.getTime() + expiration * 1000);

        if (accountEndDate == null) {
            return defaultExpiration;
        }

        Date accountExpiration = Date.from(accountEndDate.atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        return accountExpiration.before(defaultExpiration) ? accountExpiration : defaultExpiration;
    }
    /**
     * Checks if a given token is expired by comparing its expiration date with the current time.
     *
     * @param token The JWT token to be checked for expiration.
     * @return {@code true} if the token is expired, {@code false} otherwise.
     */
    public boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(clock.now());
    }

    /**
     * Retrieves the signing key for JWT token validation.
     *
     * @return The signing key used for JWT token validation.
     */
    public SecretKey getSigningKey() {
        // Keep in mind that the secret is base64 encoded
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
