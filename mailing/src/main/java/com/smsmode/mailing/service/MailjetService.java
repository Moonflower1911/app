package com.smsmode.mailing.service;

import com.mailjet.client.errors.MailjetException;
import com.smsmode.mailing.event.MailInputEvent;

/**
 * Service interface for sending emails via Mailjet.
 *
 * @author achraf (contact: achraf.taffah@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 11 nov. 2024
 */
public interface MailjetService {
    void sendEmail(MailInputEvent email) throws MailjetException;
}