/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.auth;

import lombok.Data;

/**
 * A data transfer object representing the user's email for initiating the password reset process.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
@Data
public class UserForgotPasswordPostResource {
    /**
     * The email associated with the user for password reset.
     */
    private String email;
}
