package com.smsmode.mailing.config;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 07 Nov 2024
 */
@Configuration
public class BeanConfig {
    @Value("${mailing.mailjet.api-key}")
    private String apiKey;

    @Value("${mailing.mailjet.api-secret}")
    private String apiSecret;

    @Bean
    public MailjetClient mailjetClient() {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key must be provided.");
        }

        ClientOptions options = ClientOptions.builder()
                .apiKey(apiKey)
                .apiSecretKey(apiSecret != null ? apiSecret : "")
                .build();

        return new MailjetClient(options);
    }
}
