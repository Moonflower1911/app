package com.smsmode.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for defining application beans related to load balancing.
 *
 * <p>This configuration sets up a load-balanced {@link WebClient} bean to facilitate communication
 * with other services in a load-balanced manner, leveraging the Reactor LoadBalancer exchange
 * filter function.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
@Configuration
@RequiredArgsConstructor
public class BeanConfig {

  /**
   * The load balancer exchange filter function used to apply load balancing capabilities to
   * outgoing requests made by the {@link WebClient} instance.
   */
  private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

  /**
   * Creates and configures a load-balanced {@link WebClient} bean.
   *
   * <p>This method provides a {@link WebClient} instance pre-configured with a load balancer
   * filter, which will distribute requests across available service instances based on the load
   * balancing strategy configured in the application.
   *
   * @return The load-balanced {@link WebClient} instance.
   */
  @Bean
  public WebClient webclientBuilder() {
    return WebClient.builder().filter(lbFunction).build();
  }
}
