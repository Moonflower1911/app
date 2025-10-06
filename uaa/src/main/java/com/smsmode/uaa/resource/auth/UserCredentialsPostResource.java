/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A data transfer object for handling user login credentials in authentication requests. This class
 * is used to encapsulate the username and password provided by users when they attempt to log in.
 * The username can either be a traditional username for old users or an email address for new
 * users.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Oct 2024
 */
@Getter
@Setter
@ToString
public class UserCredentialsPostResource {

    @NotEmpty
    private String username;
    @ToString.Exclude
    @NotEmpty
    private String password;
}
