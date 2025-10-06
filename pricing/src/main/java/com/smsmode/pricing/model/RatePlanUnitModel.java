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
 * <p>Created 15 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "N_RATE_PLAN_UNIT", uniqueConstraints = {
        @UniqueConstraint(name = "uk_rate_plan_unit", columnNames = {"rate_plan_id", "unit_ref_id"})
})
public class RatePlanUnitModel extends AbstractBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RATE_PLAN_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RATE_PLAN_UNIT_PLAN"))
    private RatePlanModel ratePlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT_REF_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RATE_PLAN_UNIT_UNIT"))
    private UnitRefModel unit;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;
}
