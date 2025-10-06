/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(
        name = "n_rate",
        uniqueConstraints = {
                // prevent duplicate rates for same plan + unit + date
                @UniqueConstraint(name = "uk_rate_plan_unit_date", columnNames = {"rate_plan_id", "unit_id", "rate_date"})
        },
        indexes = {
                // common query paths
                @Index(name = "idx_rate_plan_date", columnList = "rate_plan_id, rate_date"),
                @Index(name = "idx_rate_unit_date", columnList = "unit_id, rate_date"),
                @Index(name = "idx_rate_date", columnList = "rate_date")
        }
)
public class RateModel extends AbstractBaseModel {


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rate_rate_plan"))
    private RatePlanModel ratePlan;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rate_unit"))
    private UnitRefModel unit;

    @Column(name = "rate_date", nullable = false)
    private LocalDate date;

    @Column(name = "single_adult", precision = 19, scale = 2)
    private BigDecimal singleAdult;
    @Column(name = "double_adult", precision = 19, scale = 2)
    private BigDecimal doubleAdult;
    @Column(name = "extra_adult", precision = 19, scale = 2)
    private BigDecimal extraAdult;
    @Column(name = "extra_child", precision = 19, scale = 2)
    private BigDecimal extraChild;
}
