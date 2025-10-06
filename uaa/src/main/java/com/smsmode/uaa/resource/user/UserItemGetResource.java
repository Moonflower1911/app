/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.resource.user;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.resource.common.AuditGetResource;
import lombok.Data;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Mar 2025</p>
 */
@Data
public class UserItemGetResource {
    private String id;
    private String fullName;
    private String mobile;
    private String email;
    private boolean enabled;
    private boolean activated;
    private Set<RoleEnum> roles;
    private AuditGetResource audit;

}
