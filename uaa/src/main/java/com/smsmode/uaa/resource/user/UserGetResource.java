/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.user;

import lombok.Data;


/**
 * Represents the user details exposed via the API
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Oct 2024
 */
@Data
public class UserGetResource {
    private String id;
    private String fullName;
    private String email;
    private String mobile;

}
