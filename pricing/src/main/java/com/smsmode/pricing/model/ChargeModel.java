/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.embeddable.ResourceRefEmbeddable;
import com.smsmode.pricing.enumeration.ChargingBasisEnum;
import com.smsmode.pricing.enumeration.PostingTimingEnum;
import com.smsmode.pricing.model.base.AbstractBaseModel;
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
@Table(name = "n_charge")
public class ChargeModel extends AbstractBaseModel {

    private String name;
    private String code;
    private String description;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",
                    column = @Column(name = "POSTING_ACCOUNT_ID")),
            @AttributeOverride(name = "name",
                    column = @Column(name = "POSTING_ACCOUNT_NAME"))
    })
    private ResourceRefEmbeddable postingAccount;
    @Column(name = "POSTING_TIMING")
    private PostingTimingEnum postingTiming;
    @Column(name = "CHARGING_BASIS")
    private ChargingBasisEnum chargingBasis;
    @Column(name = "PACKAGE_ALLOWED")
    private boolean packageAllowed;
    @Column(name = "EXTRA_ALLOWED")
    private boolean extraAllowed;
    @Column(name = "EXCLUDE_PRICE")
    private boolean excludePrice;
    private boolean enabled;
}
