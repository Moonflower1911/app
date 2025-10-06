/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.model;

import com.smsmode.uaa.model.base.AbstractUuidBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "N_USER")
public class UserModel extends AbstractUuidBaseModel {
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "ENCRYPTED_PASSWORD")
    private String password;
    @Column(name = "IS_ACTIVATED")
    private boolean activated = false;
    @Column(name = "IS_ENABLED")
    private boolean enabled = true;
    @ManyToOne
    private AccountModel account;
    @ManyToMany()
    @JoinTable(
            name = "N_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    private Set<RoleModel> roles = new HashSet<>();

}
