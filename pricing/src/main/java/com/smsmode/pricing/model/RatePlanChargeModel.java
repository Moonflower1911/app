/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "n_rate_plan_charge", uniqueConstraints = {
        @UniqueConstraint(name = "uk_rate_plan_charge", columnNames = {"rate_plan_id", "charge_id"})
})
public class RatePlanChargeModel extends AbstractBaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RATE_PLAN_CHARGE_PLAN"))
    private RatePlanModel ratePlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RATE_PLAN_CHARGE_CHARGE"))
    private ChargeModel charge;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
}
