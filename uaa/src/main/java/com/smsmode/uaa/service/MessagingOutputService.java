/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;


import com.smsmode.uaa.event.output.EmailConfirmedOutputEvent;
import com.smsmode.uaa.event.output.MailOutputEvent;

/**
 * Service interface for sending output events. This service is responsible for processing and
 * dispatching events, typically to an external messaging system broker.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
public interface MessagingOutputService {

    /**
     * Asynchronously sends a mail event to the specified messaging exchange.
     *
     * @param mailOutputEvent the event containing the email details (such as recipient, subject,
     *                        variables, etc.) to be sent.
     */
    void sendMailEvent(MailOutputEvent mailOutputEvent);

    void sendEmailConfirmedEvent(EmailConfirmedOutputEvent emailConfirmedOutputEvent);
}
