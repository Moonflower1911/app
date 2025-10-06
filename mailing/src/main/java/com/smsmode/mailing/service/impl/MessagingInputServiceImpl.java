package com.smsmode.mailing.service.impl;

import com.mailjet.client.errors.MailjetException;
import com.smsmode.mailing.event.MailInputEvent;
import com.smsmode.mailing.service.MailjetService;
import com.smsmode.mailing.service.MessagingInputService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * Service implementation for handling incoming mailing events.
 *
 * @author achraf (contact: achraf.taffah@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 01 nov. 2024
 */
@Slf4j
@Service
@AllArgsConstructor
public class MessagingInputServiceImpl implements MessagingInputService {
    private MailjetService mailService;

    /**
     * Defines a Bean to process MailInputEvent. This Consumer function listens to incoming events and
     * processes them by validating recipients and triggering the sending of emails based on the event
     * details.
     *
     * @return a Consumer that handles MailInputEvent instances
     */
    @Bean
    public Consumer<MailInputEvent> processMailingInputEvent() {
        return mailInputEvent -> {
            log.info("Received event with payload: {}", mailInputEvent);
            try {
                // Attempt to send the email
                mailService.sendEmail(mailInputEvent);
                log.info("Email sent successfully to {}", mailInputEvent.to());

            } catch (MailjetException e) {
                log.error("Failed to send email due to Mailjet service error: {}", e.getMessage(), e);
                // Implement optional retry logic here or alerting if needed

            } catch (IllegalArgumentException e) {
                log.error("Invalid input in MailInputEvent: {}", e.getMessage(), e);
                // Consider notifying the developer team about malformed event data for investigation

            } catch (NullPointerException e) {
                log.error("Null value encountered in MailInputEvent: {}", e.getMessage(), e);
                // Log for further investigation or handle the null gracefully if possible

            } catch (Exception e) {
                log.error(
                        "An unexpected error occurred while processing the mailing event: {}",
                        e.getMessage(),
                        e);
            }
        };
    }
}
