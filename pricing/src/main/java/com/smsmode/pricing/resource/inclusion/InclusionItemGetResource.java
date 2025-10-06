/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.inclusion;

import com.smsmode.pricing.enumeration.ChargingBasisEnum;
import com.smsmode.pricing.enumeration.PostingTimingEnum;
import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
@Data
public class InclusionItemGetResource {
    private String id;
    private ResourceCodeRefGetResource charge;
    private ResourceRefGetResource ratePlan;
    private BigDecimal amount;
    private ChargingBasisEnum chargingBasis;
    private PostingTimingEnum postingTiming;
    private boolean enabled;
}
