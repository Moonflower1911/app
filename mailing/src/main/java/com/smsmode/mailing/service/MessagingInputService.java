package com.smsmode.mailing.service;

import com.smsmode.mailing.event.MailInputEvent;

import java.util.function.Consumer;

/**
 * Interface for handling and processing mailing input events within the messaging system.
 *
 * @author achraf (contact: achraf.taffah@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 01 nov. 2024
 */
public interface MessagingInputService {
    Consumer<MailInputEvent> processMailingInputEvent();
}
