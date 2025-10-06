/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.embeddable.AgeBucketEmbeddable;
import com.smsmode.pricing.enumeration.AmountTypeEnum;
import com.smsmode.pricing.enumeration.GuestTypeEnum;
import com.smsmode.pricing.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "GUEST_RATE_RULE")
public class GuestRateRuleModel extends AbstractBaseModel {
    @Column(name = "guest_threshold")
    private int guestThreshold;
    @Enumerated(EnumType.STRING)
    @Column(name = "guest_type")
    private GuestTypeEnum guestType;
    @Embedded
    private AgeBucketEmbeddable ageBucket;
    @Enumerated(EnumType.STRING)
    @Column(name = "amount_type")
    private AmountTypeEnum amountType;
    private BigDecimal value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUEST_RATE_STRATEGY_ID")
    private GuestRateStrategyModel strategy;
}
