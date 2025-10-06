/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.event.output.EmailConfirmedOutputEvent;
import com.smsmode.uaa.event.output.MailOutputEvent;
import com.smsmode.uaa.service.MessagingOutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link MessagingOutputService} interface, responsible for sending mail
 * events to a specified messaging exchange using Spring Cloud Stream.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingOutputServiceImpl implements MessagingOutputService {

    private final StreamBridge streamBridge;
    @Value("${spring.cloud.stream.exchange.send-email}")
    String mailExchange;
    @Value("${spring.cloud.stream.exchange.email-confirmed}")
    String emailConfirmedExchange;

    /**
     * {@inheritDoc}
     *
     * <p>This method is executed asynchronously to avoid blocking the main application flow * while
     * the event is being sent.
     */
    @Async
    @Override
    public void sendMailEvent(MailOutputEvent mailOutputEvent) {
        log.debug(
                "Sending event to exchange: <{}> with payload: {} ...", mailExchange, mailOutputEvent);
        streamBridge.send(mailExchange, mailOutputEvent);
        log.info("Mail event sent");
    }

    @Override
    public void sendEmailConfirmedEvent(EmailConfirmedOutputEvent emailConfirmedOutputEvent) {
        log.debug(
                "Sending event to exchange: <{}> with payload: {} ...", emailConfirmedExchange, emailConfirmedOutputEvent);
        streamBridge.send(emailConfirmedExchange, emailConfirmedOutputEvent);
        log.info("Email confirmed event sent");
    }
}
