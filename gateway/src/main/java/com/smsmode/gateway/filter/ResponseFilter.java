package com.smsmode.gateway.filter;

import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * A response filter that adds a correlation ID (trace ID) to outbound response headers.
 *
 * <p>This filter retrieves the current trace ID using Micrometer's Tracer and injects it into the
 * response header with the key "tmx-correlation-id".
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 21 Nov 2024
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ResponseFilter {

  private final Tracer tracer;

  private static final String CORRELATION_ID = "tmx-correlation-id";

  /**
   * Defines a global post filter for adding the trace ID to response headers after processing.
   *
   * @return a {@link GlobalFilter} instance that performs the filtering.
   */
  @Bean
  public GlobalFilter postGlobalFilter() {
    return (exchange, chain) -> {
      final String traceId =
          Optional.ofNullable(tracer.currentSpan())
              .map(value -> value.context().traceIdString())
              .orElse(null);

      return chain
          .filter(exchange)
          .then(
              Mono.fromRunnable(
                  () -> {
                    log.debug("Adding the correlation id to the outbound headers. {}", traceId);
                    exchange.getResponse().getHeaders().add(CORRELATION_ID, traceId);
                    log.debug(
                        "Completing outgoing request for {}.", exchange.getRequest().getURI());
                  }));
    };
  }
}
