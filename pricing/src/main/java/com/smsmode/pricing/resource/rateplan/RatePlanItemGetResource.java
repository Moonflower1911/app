/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rateplan;

import com.smsmode.pricing.enumeration.RatePlanTypeEnum;
import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import lombok.Data;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 02 Sep 2025</p>
 */
@Data
public class RatePlanItemGetResource {
    private String id;
    private String code;
    private String name;
    private RatePlanTypeEnum type;
    private ResourceRefGetResource parent;
    private boolean enabled;
    private Set<ResourceCodeRefGetResource> units;
}
