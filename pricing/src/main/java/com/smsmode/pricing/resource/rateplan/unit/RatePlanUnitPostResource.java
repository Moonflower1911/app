/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rateplan.unit;

import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
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
public class RatePlanUnitPostResource {
    private ResourceCodeRefGetResource unit;
    private String ratePlanId;
    private boolean enabled;
}
