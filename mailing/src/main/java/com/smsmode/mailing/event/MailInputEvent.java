package com.smsmode.mailing.event;

import java.util.List;
import java.util.Map;

public record MailInputEvent(
        List<String> to,
        List<String> cc,
        List<String> bcc,
        String subject,
        int templateId,
        Map<String, String> variables) {
}
