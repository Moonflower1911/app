/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.security;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.exception.AuthenticationUnauthorizedException;
import com.smsmode.uaa.exception.enumeration.AuthenticationUnauthorizedExceptionTitleEnum;
import com.smsmode.uaa.service.JwtTokenProviderService;
import com.smsmode.uaa.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.smsmode.uaa.util.SecurityUtil.*;


/**
 * Custom JWT authentication filter for processing HTTP requests. Extends {@link
 * OncePerRequestFilter}.
 *
 * <p>This filter retrieves the authentication token from the HTTP request header, verifies its
 * integrity using the {@link JwtTokenProviderService}, and sets necessary user information in
 * {@link UserContextHolder} to be used during the request lifecycle.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 17 Oct 2024
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProviderService jwtTokenProviderService;

    /**
     * This method is responsible for filtering incoming HTTP requests to authenticate the user based
     * on a JWT token. It extracts and validates the JWT token from the Authorization header,
     * processes the claims, and sets the authentication context for the user if the token is valid
     *
     * @param request     The HTTP request being processed.
     * @param response    The HTTP response to be sent.
     * @param filterChain The filter chain that allows the request to continue processing.
     * @throws ServletException If a servlet exception occurs during the filtering process.
     * @throws IOException      If an I/O error occurs during the filtering process.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("Processing authentication for request '{}'", request.getRequestURI());
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!ObjectUtils.isEmpty(authToken) && authToken.startsWith(SecurityUtil.TOKEN_TYPE)) {
            try {
                Claims claims = jwtTokenProviderService.getClaimsFromToken(authToken);
                String username = claims.get(USERNAME_KEY).toString();
                String userId = claims.get(IDENTIFIER_KEY).toString();
                String authoritiesToken = String.join(",", claims.get(AUTHORITIES_KEY).toString());
                UserContextHolder.getContext().setFullName(claims.getSubject());
                UserContextHolder.getContext().setUserId(userId);
                UserContextHolder.getContext().setEmail(username);
                Set<? extends GrantedAuthority> authorities =
                        Arrays.stream(authoritiesToken.split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toSet());
                UserContextHolder.getContext()
                        .setRoles(
                                authorities.stream()
                                        .map(r -> RoleEnum.valueOf(SecurityUtil.removeRolePrefix(r.getAuthority())))
                                        .collect(Collectors.toSet()));
                log.info(
                        "User is: '{}' with email: '{}' and authorities: '{}'",
                        UserContextHolder.getCurrentUserDisplayName(),
                        username,
                        authoritiesToken);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                log.warn(
                        "An error occurred during token validation to retrieve claims: {}", e.getMessage());
                if (e instanceof ExpiredJwtException) {
                    throw new AuthenticationUnauthorizedException(
                            AuthenticationUnauthorizedExceptionTitleEnum.EXPIRED_TOKEN, "Your token has expired");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Determines whether the request should be filtered.
     *
     * <p>Excludes requests to actuator and OpenApi definition from filtering.
     *
     * @param request the HTTP request to check
     * @return true if the request matches excluded paths, false otherwise
     * @throws ServletException if an error occurs during filtering
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match("/actuator/**", request.getServletPath())
                || antPathMatcher.match("/v3/**", request.getServletPath());
    }
}
