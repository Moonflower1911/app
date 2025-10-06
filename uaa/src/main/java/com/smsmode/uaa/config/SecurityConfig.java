/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.config;

import com.smsmode.uaa.security.JwtAuthenticationFilter;
import com.smsmode.uaa.service.LocalUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

/**
 * Configuration class for defining security configurations in the application. This class is
 * annotated with {@link Configuration}, {@link EnableWebSecurity}, and {@link EnableMethodSecurity}
 * to enable and customize Spring Security features.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Oct 2024
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final LocalUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Creates and configures a {@link DaoAuthenticationProvider} bean for authentication. The
     * provider uses a custom {@link LocalUserDetailsService} and a {@link PasswordEncoder}.
     *
     * @return The configured {@link DaoAuthenticationProvider} bean.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates and configures an {@link AuthenticationManager} bean for authentication.
     *
     * @param authConfig The {@link AuthenticationConfiguration} to obtain the authentication manager.
     * @return The configured {@link AuthenticationManager} bean.
     * @throws Exception if an error occurs while configuring the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http The {@link HttpSecurity} object to configure.
     * @return The configured {@link SecurityFilterChain} for the application.
     * @throws Exception if an error occurs while configuring the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/login")
                                        .permitAll()
                                        .requestMatchers("/reset-password")
                                        .permitAll()
                                        .requestMatchers("/refresh-token")
                                        .permitAll()
                                        .requestMatchers("/validate-account")
                                        .permitAll()
                                        .requestMatchers("/forgot-password")
                                        .permitAll()
                                        .requestMatchers("/validate-token")
                                        .permitAll()
                                        .requestMatchers("/actuator/**")
                                        .permitAll()
                                        .requestMatchers("/v3/api-docs/**")
                                        .permitAll()
                                        .requestMatchers("/v3/api-docs")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
