/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.model;

import com.smsmode.uaa.model.base.AbstractIntBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Aug 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "N_ACCOUNT")
public class AccountModel extends AbstractIntBaseModel {

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "IS_ENABLED")
    private boolean enabled = true;

    private LocalDate startDate;
    private LocalDate endDate;
}
