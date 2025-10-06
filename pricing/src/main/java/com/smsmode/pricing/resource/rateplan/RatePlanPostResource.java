/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rateplan;

import com.smsmode.pricing.enumeration.RatePlanTypeEnum;
import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import com.smsmode.pricing.validator.UniqueRatePlanCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Getter
@Setter
public class RatePlanPostResource {
    private String name;
    @UniqueRatePlanCode
    private String code;
    private String description;
    private RatePlanTypeEnum type = RatePlanTypeEnum.BASE;
    private boolean enabled = true;
    private List<ResourceCodeRefGetResource> units;
}
