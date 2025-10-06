/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.event.output;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Represents the data structure for sending email details through an output event. Contains
 * information about recipients, subject, template ID, and email variables.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
@Data
public class MailOutputEvent {
    /**
     * The list of recipients (To) for the email.
     */
    private List<String> to;

    /**
     * The list of recipients in carbon copy (CC) for the email.
     */
    private List<String> cc;

    /**
     * The list of recipients in blind carbon copy (BCC) for the email.
     */
    private List<String> bcc;

    private String subject;

    private int templateId;

    /**
     * The variables the email.
     */
    private Map<String, String> variables;
}
