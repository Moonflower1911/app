package com.smsmode.gateway.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for tracing and metrics capabilities in the application.
 *
 * <p>This configuration defines beans that apply selective tracing exclusions for specific
 * endpoints and integrates Micrometer support with a {@link MeterRegistry}. Certain paths, such as
 * Actuator endpoints and Swagger documentation, are excluded from tracing to avoid unnecessary span
 * generation for non-business-critical endpoints.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 11 Nov 2024
 */
@Configuration
public class TracingConfig {

  /**
   * Excludes Actuator endpoints from span export to reduce noise in tracing by ignoring spans that
   * target paths starting with "/actuator".
   *
   * @return a {@link SpanExportingPredicate} that filters out Actuator spans.
   */
  @Bean
  SpanExportingPredicate noActuator() {
    return span ->
            span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/actuator");
  }

  /**
   * Excludes Swagger endpoints from span export, ensuring that requests to paths starting with
   * "/swagger" do not generate unnecessary tracing spans.
   *
   * @return a {@link SpanExportingPredicate} that filters out Swagger spans.
   */
  @Bean
  SpanExportingPredicate noSwagger() {
    return span ->
        span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/swagger");
  }

  /**
   * Excludes API documentation endpoints from span export, specifically targeting requests to paths
   * starting with "/v3/api-docs" to prevent extraneous spans in tracing.
   *
   * @return a {@link SpanExportingPredicate} that filters out API documentation spans.
   */
  @Bean
  SpanExportingPredicate noApiDocs() {
    return span ->
        span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/v3/api-docs");
  }

  /**
   * Configures Micrometer capability support using the provided {@link MeterRegistry}, allowing for
   * the collection and export of application metrics.
   *
   * @param registry the {@link MeterRegistry} instance for managing metrics.
   * @return a {@link Capability} that integrates Micrometer metrics into the tracing configuration.
   */
  @Bean
  public Capability capability(final MeterRegistry registry) {
    return new MicrometerCapability(registry);
  }
}
