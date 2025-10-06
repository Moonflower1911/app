/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * A data transfer object representing user input for resetting the password.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
@Data
public class UserResetPasswordPostResource {
    @NotEmpty
    private String resetPasswordKey;

    @NotEmpty
    private String password;
}
