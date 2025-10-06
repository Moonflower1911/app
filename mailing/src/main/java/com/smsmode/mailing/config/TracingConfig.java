package com.smsmode.mailing.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.exporter.SpanExportingPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 *     file, via any medium is strictly prohibited Proprietary and confidential
 *     <p>Created 11 Nov 2024
 */
@Configuration
public class TracingConfig {

  @Bean
  SpanExportingPredicate noActuator() {
    return span ->
        span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/actuator");
  }

  @Bean
  SpanExportingPredicate noSwagger() {
    return span ->
        span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/swagger");
  }

  @Bean
  SpanExportingPredicate noApiDocs() {
    return span ->
        span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/v3/api-docs");
  }

  @Bean
  public Capability capability(final MeterRegistry registry) {
    return new MicrometerCapability(registry);
  }
}
