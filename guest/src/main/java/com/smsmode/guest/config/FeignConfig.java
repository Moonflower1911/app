/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created Dec 04, 2024
 */
@Configuration
@EnableFeignClients(basePackages = {"com.smsmode.guest.service.feign"})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class FeignConfig {

    @Component
    public static class CurrentRequestHeadersInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest webRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
                webRequest
                        .getHeaderNames()
                        .asIterator()
                        .forEachRemaining(
                                h -> {
                                    if (!h.equalsIgnoreCase("content-length"))
                                        requestTemplate.header(h, webRequest.getHeader(h));
                                });
            }
        }
    }
}
