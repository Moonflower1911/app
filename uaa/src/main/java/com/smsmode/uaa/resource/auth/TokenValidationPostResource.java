/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.auth;

import lombok.Data;

/**
 * Represents a resource object for validating a token. Contains the token that needs to be
 * validated.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Oct 2024
 */
@Data
public class TokenValidationPostResource {
    private String token;
}
