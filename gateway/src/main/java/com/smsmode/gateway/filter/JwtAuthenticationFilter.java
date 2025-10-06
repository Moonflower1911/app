package com.smsmode.gateway.filter;

import com.smsmode.gateway.dao.service.TokenDaoService;
import com.smsmode.gateway.dao.specification.TokenSpecification;
import com.smsmode.gateway.exception.AuthorizationForbiddenException;
import com.smsmode.gateway.exception.enumeration.AuthorizationForbiddenExceptionTitleEnum;
import com.smsmode.gateway.model.TokenModel;
import com.smsmode.gateway.resource.TokenValidationPostResource;
import com.smsmode.gateway.service.JwtTokenProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * Global filter for JWT authentication and validation in the Gateway service. This filter
 * intercepts requests, checks for the presence of a JWT token in the authorization header,
 * validates the token, and determines its presence in the database. If not found, it contacts the
 * UAA service to validate the token and save it.
 *
 * <p>This filter performs the following actions:
 *
 * <ul>
 *   <li>Checks for an authorization token in the request header.
 *   <li>Validates token expiration and throws an exception if expired.
 *   <li>Verifies token existence in the database, and if absent, calls the UAA service for
 *       validation.
 *   <li>Saves validated tokens in the database to avoid repeated external calls.
 * </ul>
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

  private final JwtTokenProviderService jwtTokenProviderService;
  private final TokenDaoService tokenDaoService;
  private final WebClient webClient;
  private static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * Filters incoming requests to check for an authorization token and handle its validation.
   *
   * @param exchange the current server exchange containing the request and response
   * @param chain the Gateway filter chain to continue processing
   * @return a {@link Mono} indicating filter chain execution or error if the token is invalid
   */
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    List<String> headers = exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER);
    if (CollectionUtils.isEmpty(headers)) {
      log.trace("Request received without an authorization token.");
      return chain.filter(exchange);
    } else {
      String authToken = headers.getFirst();
      log.debug("Checking if token has expired ...");
      if (jwtTokenProviderService.isTokenExpired(authToken)) {
        log.info("Token has expired. Will throw an error ...");
        throw new AuthorizationForbiddenException(
            AuthorizationForbiddenExceptionTitleEnum.TOKEN_NOT_VALID, "Token is expired");
      } else {
        log.info("Checking if the token is valid and already saved in database ...");

        return Mono.fromCallable(
                () -> tokenDaoService.existsBy(TokenSpecification.withToken(authToken)))
            .subscribeOn(Schedulers.boundedElastic()) // Use a bounded thread pool for blocking I/O
            .flatMap(
                exists -> {
                  if (exists) {
                    log.debug("Token found in the database. Proceeding with request.");
                    return chain.filter(exchange);
                  } else {
                    log.debug("Token not found in database. Validating with UAA service...");
                    return validateAndSaveToken(authToken, exchange, chain);
                  }
                });
      }
    }
  }

  /**
   * Calls the UAA service to validate and save a token if it does not already exist in the
   * database. If valid, saves the token to avoid repeated UAA calls for future requests.
   *
   * @param authToken the token to validate and save
   * @param exchange the current server exchange containing the request and response
   * @param chain the Gateway filter chain to continue processing
   * @return a {@link Mono} indicating filter chain execution or error if the token is invalid
   */
  private Mono<Void> validateAndSaveToken(
      String authToken, ServerWebExchange exchange, GatewayFilterChain chain) {
    return webClient
        .post()
        .uri("http://uaa/validate-token")
        .body(
            Mono.just(new TokenValidationPostResource(authToken)),
            TokenValidationPostResource.class)
        .retrieve()
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            response -> {
              log.debug("Token validation failed by UAA service with client/server error.");
              return Mono.error(
                  new AuthorizationForbiddenException(
                      AuthorizationForbiddenExceptionTitleEnum.TOKEN_NOT_VALID,
                      "Token is not valid"));
            })
        .bodyToMono(Void.class) // Handle the response body if needed, but here we expect none
        .then(
            Mono.fromRunnable(
                () -> {
                  log.debug("Token validated by UAA service. Saving to database.");
                  TokenModel token = new TokenModel();
                  token.setValue(authToken);
                  tokenDaoService.save(token);
                  log.info("Token successfully saved: {}", token.getUuid());
                }))
        .then(chain.filter(exchange)); // Continue the filter chain after saving
  }
}
