package com.smsmode.mailing.service.impl;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import com.smsmode.mailing.event.MailInputEvent;
import com.smsmode.mailing.service.MailjetService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailjetServiceImpl implements MailjetService {

    private final MailjetClient client;

    @Value("${mailing.mailjet.sender.email}")
    private String senderEmail;

    @Value("${mailing.mailjet.sender.name}")
    private String senderName;

    public MailjetServiceImpl(MailjetClient client) {
        this.client = client;
    }

    @Override
    public void sendEmail(MailInputEvent email) throws MailjetException {
        if (email == null) {
            throw new MailjetException("Error sending email: Email object is null");
        }

        // Check if recipients list is empty
        if (email.to() == null || email.to().isEmpty()) {
            throw new MailjetException("No recipients provided");
        }

        if (email.subject() == null || email.subject().isEmpty()) {
            throw new MailjetException("Error sending email: Subject cannot be empty");
        }

        if (email.variables() == null) {
            throw new MailjetException("Email variables cannot be null");
        }

        // Prepare destination mails
        JSONArray destinationEmails = new JSONArray();
        email.to().stream()
                .map(
                        to -> {
                            JSONObject emailObject = new JSONObject();
                            emailObject.put(Emailv31.Message.EMAIL, to);
                            return emailObject;
                        })
                .forEach(destinationEmails::put);

        // Fill variables
        JSONObject variables = new JSONObject();
        email.variables().forEach(variables::put);

        // Create the email request
        MailjetRequest request =
                new MailjetRequest(Emailv31.resource)
                        .property(
                                Emailv31.MESSAGES,
                                new JSONArray()
                                        .put(
                                                new JSONObject()
                                                        .put(
                                                                Emailv31.Message.FROM,
                                                                new JSONObject()
                                                                        .put(Emailv31.Message.EMAIL, senderEmail)
                                                                        .put(Emailv31.Message.NAME, senderName))
                                                        .put(Emailv31.Message.TO, destinationEmails)
                                                        .put(Emailv31.Message.SUBJECT, email.subject())
                                                        .put(Emailv31.Message.TEMPLATEID, email.templateId())
                                                        .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                                        .put(Emailv31.Message.VARIABLES, variables)));

        // Send the email and get the response
        MailjetResponse response = client.post(request);

        // Check the response status
        if (response.getStatus() != 200) {
            throw new MailjetException("Client error");
        }
    }
}
