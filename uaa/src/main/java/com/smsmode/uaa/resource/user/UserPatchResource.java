/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.user;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.validator.UniqueEmail;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Apr 2025</p>
 */
@Data
public class UserPatchResource {
    private String fullName;
    @UniqueEmail
    private String email;
    private String mobile;
    @Size(min = 1)
    private Set<RoleEnum> roles;
    private Boolean enabled;
}
