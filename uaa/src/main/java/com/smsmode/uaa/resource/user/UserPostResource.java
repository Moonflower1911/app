/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.user;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.validator.UniqueEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Apr 2025</p>
 */
@Data
public class UserPostResource {
    @NotEmpty
    private String fullName;
    @NotEmpty
    @UniqueEmail
    private String email;
    private String mobile;
    @Size(min = 1)
    @NotNull
    private Set<RoleEnum> roles;
}
