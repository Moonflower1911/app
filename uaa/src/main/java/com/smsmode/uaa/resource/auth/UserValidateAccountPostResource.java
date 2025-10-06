/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * A data transfer object representing user input for activation user account.
 *
 * @author mohamed (contact: mohamed.amchia@messaging-technologies.com)
 * <p>Created 14 Oct 2024
 */
@Data
public class UserValidateAccountPostResource {
    @NotEmpty
    private String activationKey;
    private String password;
}
