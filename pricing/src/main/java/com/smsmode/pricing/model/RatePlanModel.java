/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.enumeration.RatePlanTypeEnum;
import com.smsmode.pricing.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "N_RATE_PLAN")
public class RatePlanModel extends AbstractBaseModel {
    private String name;
    @Column(unique = true, updatable = false)
    private String code;
    private String description;
    private RatePlanTypeEnum type = RatePlanTypeEnum.BASE;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private RatePlanModel parent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANCELLATION_POLICY_ID")
    private CancellationPolicyModel cancellationPolicy;
    @Column(name = "min_los", nullable = false)
    private Integer minLos = 1;
    @Column(name = "max_los")
    private Integer maxLos;
    @Column(name = "min_lead", nullable = false)
    private Integer minLead = 0;
    @Column(name = "max_lead")
    private Integer maxLead;
    private boolean enabled = false;
    @OneToMany(mappedBy = "ratePlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RatePlanUnitModel> ratePlanUnits = new HashSet<>();

    // convenience helper to still access UnitRefModel directly
    @Transient
    public Set<UnitRefModel> getUnits() {
        return ratePlanUnits.stream()
                .map(RatePlanUnitModel::getUnit)
                .collect(Collectors.toSet());
    }

}
