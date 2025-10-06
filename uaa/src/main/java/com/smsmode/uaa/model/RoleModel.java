/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.model;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.model.base.AbstractIntBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Dec 2024
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "N_ROLE")
public class RoleModel extends AbstractIntBaseModel implements GrantedAuthority {

    @Column(name = "NAME", length = 90)
    @Enumerated(value = EnumType.STRING)
    private RoleEnum name;

    public RoleModel(RoleEnum roleEnum) {
        this.name = roleEnum;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + getName().name();
    }

}
