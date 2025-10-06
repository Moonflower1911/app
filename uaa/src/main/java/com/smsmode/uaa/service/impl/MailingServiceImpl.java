/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.event.output.MailOutputEvent;
import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.service.MailingService;
import com.smsmode.uaa.service.MessagingOutputService;
import com.smsmode.uaa.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link MailingService} that handles sending various types of email
 * notifications related to account validation and password reset for users. This service utilizes
 * the {@link MessagingOutputService} to send email events with the appropriate templates and
 * variables for different types of email communications.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    private static final String FULL_NAME = "fullName";
    private static final String EMAIL_ADDRESS = "emailAddress";
    private final MessagingOutputService messagingOutputService;

    @Value("${ui.client.url}")
    public String smsModeClientUrl;
    @Value("${ui.admin.url}")
    public String smsModeAdminUrl;
    // Constants for query parameters
    @Value("${mail.template.organisation_member_reset_password_confirmation}")
    private int resetPasswordConfirmationTemplateId;
    @Value("${mail.template.organisation_member_reset_password}")
    private int resetPasswordTemplateId;
    @Value("${mail.template.account-validation}")
    private int accountValidationTemplateId;
    @Value("${mail.template.organisation_member_account_confirmation_after_validation}")
    private int organisationMemberAccountConfirmationAfterValidation;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendResetPasswordEmail(UserModel loginModel, TokenModel tokenModel) {
        MailOutputEvent mailOutputEvent = new MailOutputEvent();
        mailOutputEvent.setTo(List.of(loginModel.getEmail()));
        mailOutputEvent.setSubject("xStay - Forgot your password?");
        Map<String, String> variables = new HashMap<>();
        variables.put(FULL_NAME, loginModel.getFullName());

        StringBuilder accountValidationLinkBuilder;
        if (SecurityUtil.isAdmin(loginModel)) {
            accountValidationLinkBuilder =
                    new StringBuilder(smsModeAdminUrl)
                            .append("/#/reset-password?token=")
                            .append(tokenModel.getValue());
        } else {
            accountValidationLinkBuilder =
                    new StringBuilder(smsModeClientUrl)
                            .append("/#/reset-password?token=")
                            .append(tokenModel.getValue());
        }
        variables.put("accountValidationLink", accountValidationLinkBuilder.toString());
        variables.put("resetKeyExpDate", tokenModel.getExpirationDate().toString());
        mailOutputEvent.setVariables(variables);
        mailOutputEvent.setTemplateId(resetPasswordTemplateId);
        messagingOutputService.sendMailEvent(mailOutputEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendResetPasswordConfirmationEmail(UserModel user) {
        MailOutputEvent mailOutputEvent = new MailOutputEvent();
        mailOutputEvent.setTo(List.of(user.getEmail()));
        mailOutputEvent.setSubject("xStay - New password confirmation");
        Map<String, String> variables = new HashMap<>();
        variables.put(FULL_NAME, user.getFullName());
        variables.put(EMAIL_ADDRESS, user.getEmail());
        mailOutputEvent.setVariables(variables);
        mailOutputEvent.setTemplateId(resetPasswordConfirmationTemplateId);
        messagingOutputService.sendMailEvent(mailOutputEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendActivationAccountConfirmationEmail(UserModel user) {
        MailOutputEvent mailOutputEvent = new MailOutputEvent();
        mailOutputEvent.setTo(List.of(user.getEmail()));
        mailOutputEvent.setSubject("xStay - Account activated");
        Map<String, String> variables = new HashMap<>();
        variables.put(FULL_NAME, user.getFullName());
        mailOutputEvent.setVariables(variables);
        mailOutputEvent.setTemplateId(organisationMemberAccountConfirmationAfterValidation);
        messagingOutputService.sendMailEvent(mailOutputEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendActivationAccountEmail(
            UserModel user,
            TokenModel tokenModel) {

        MailOutputEvent mailOutputEvent = new MailOutputEvent();
        mailOutputEvent.setTo(List.of(user.getEmail()));
        mailOutputEvent.setSubject("xStay - Activate your account");
        Map<String, String> variables = new HashMap<>();
        variables.put(FULL_NAME, user.getFullName());

        StringBuilder accountValidationLinkBuilder;
        if (SecurityUtil.isAdmin(user)) {
            accountValidationLinkBuilder =
                    new StringBuilder(smsModeAdminUrl)
                            .append("/#/account-validation?token=")
                            .append(tokenModel.getValue());
        } else {
            accountValidationLinkBuilder =
                    new StringBuilder(smsModeClientUrl)
                            .append("/#/account-validation?token=")
                            .append(tokenModel.getValue());
        }
        variables.put("accountValidationLink", accountValidationLinkBuilder.toString());
        variables.put("resetKeyExpDate", tokenModel.getExpirationDate().toString());
        mailOutputEvent.setVariables(variables);
        mailOutputEvent.setTemplateId(accountValidationTemplateId);
        messagingOutputService.sendMailEvent(mailOutputEvent);
    }
}
