/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.resource.vatrule;

import com.smsmode.invoice.enumeration.AmountTypeEnum;
import com.smsmode.invoice.enumeration.ApplicationTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
@Data
public class VatRulePostResource {
    private String name;
    private BigDecimal amount;
    private AmountTypeEnum amountType;
    private ApplicationTypeEnum application;
    private String description;
    private boolean enabled;
}
