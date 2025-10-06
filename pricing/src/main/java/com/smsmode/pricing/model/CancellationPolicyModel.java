/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.embeddable.ResourceRefEmbeddable;
import com.smsmode.pricing.enumeration.CancellationPolicyTypeEnum;
import com.smsmode.pricing.enumeration.CancellationTimingTypeEnum;
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
 * <p>Created 16 Sep 2025</p>
 */
@Entity
@Getter
@Setter
@Table(name = "N_CANCELLATION_POLICY")
public class CancellationPolicyModel extends AbstractBaseModel {
    private String name;
    private String code;
    private String description;
    @Enumerated(EnumType.STRING)
    private CancellationPolicyTypeEnum type;
    @Enumerated(EnumType.STRING)
    private CancellationTimingTypeEnum timingType;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",
                    column = @Column(name = "POSTING_ACCOUNT_ID")),
            @AttributeOverride(name = "name",
                    column = @Column(name = "POSTING_ACCOUNT_NAME"))
    })
    private ResourceRefEmbeddable postingAccount;
    @Enumerated(EnumType.STRING)
    private PostingTimingEnum postingTiming;
    private int hours;
    private BigDecimal amount;
    private boolean enabled;
}
