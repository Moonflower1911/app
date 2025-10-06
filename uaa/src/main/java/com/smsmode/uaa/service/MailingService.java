/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;


import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.UserModel;

/**
 * The {@code MailingService} interface defines methods for sending various types of emails related
 * to user accounts, such as password reset, account activation, and activation confirmation emails.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
public interface MailingService {

    void sendResetPasswordEmail(UserModel loginModel, TokenModel tokenModel);

    void sendResetPasswordConfirmationEmail(UserModel loginModel);

    /**
     * Sends an account activation confirmation email to the user after their account has been
     * successfully activated.
     *
     * @param loginModel the user whose account has been activated and to whom the confirmation email
     *                   will be sent
     */
    void sendActivationAccountConfirmationEmail(UserModel loginModel);

    /**
     * Sends an email to the user to activate their account.
     *
     * @param user              the user whose account needs to be activated

     */
    void sendActivationAccountEmail(
            UserModel user, TokenModel tokenModel);

}
