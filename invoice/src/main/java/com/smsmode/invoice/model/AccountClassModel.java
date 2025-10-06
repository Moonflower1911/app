package com.smsmode.invoice.model;

import com.smsmode.invoice.model.base.AbstractBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "N_ACCOUNT_CLASS")
public class AccountClassModel extends AbstractBaseModel {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IS_SYSTEM_CRITICAL", nullable = false, updatable = false)
    private boolean isSystemCritical = false;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;
}
