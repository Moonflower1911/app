/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rateplan;

import com.smsmode.pricing.enumeration.RatePlanTypeEnum;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import com.smsmode.pricing.resource.old.common.AuditGetResource;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Data
public class RatePlanGetResource {
    private String id;
    private String name;
    private String code;
    private String description;
    private RatePlanTypeEnum type;
    private RatePlanGetResource parent;
    private ResourceRefGetResource cancellationPolicy;
    private AuditGetResource audit;
    private RestrictionsPatchResource restrictions;
}
