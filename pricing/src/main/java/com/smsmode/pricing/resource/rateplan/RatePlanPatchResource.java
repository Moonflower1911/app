/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rateplan;

import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 02 Sep 2025</p>
 */
@Getter
@Setter
public class RatePlanPatchResource {
    private String name;
    private String description;
    private Boolean enabled;
    private String cancellationPolicyId;
    private List<ResourceCodeRefGetResource> units;
    private RestrictionsPatchResource restrictions;
}
