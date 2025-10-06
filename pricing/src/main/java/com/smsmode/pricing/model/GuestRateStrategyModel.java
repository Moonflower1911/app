
/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "N_GUEST_RATE_STRATEGY")
public class GuestRateStrategyModel extends AbstractBaseModel {

    private String name;
    private String description;
    private boolean enabled = false;

    @OneToMany(mappedBy = "strategy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("createdAt ASC")
    private List<GuestRateRuleModel> rules = new ArrayList<>();

}
