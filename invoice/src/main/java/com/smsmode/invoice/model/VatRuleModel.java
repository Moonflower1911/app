/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.model;

import com.smsmode.invoice.enumeration.AmountTypeEnum;
import com.smsmode.invoice.enumeration.ApplicationTypeEnum;
import com.smsmode.invoice.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "N_VAT_RULE")
public class VatRuleModel extends AbstractBaseModel {
    private String name;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "amount_type")
    private AmountTypeEnum amountType;
    @Enumerated(EnumType.STRING)
    @Column(name = "application")
    private ApplicationTypeEnum application;
    @Column(name = "description")
    private String description;
    private boolean enabled;
}
