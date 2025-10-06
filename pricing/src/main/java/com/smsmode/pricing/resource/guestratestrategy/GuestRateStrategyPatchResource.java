/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.guestratestrategy;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Sep 2025</p>
 */
@Data
public class GuestRateStrategyPatchResource {
    private String name;
    private String description;
    private Boolean enabled;
    @Valid
    private List<@Valid GuestRateRulePostResource> rules;
}
