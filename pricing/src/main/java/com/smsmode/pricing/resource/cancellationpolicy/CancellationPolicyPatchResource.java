/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.cancellationpolicy;

import com.smsmode.pricing.embeddable.ResourceRefEmbeddable;
import com.smsmode.pricing.enumeration.CancellationPolicyTypeEnum;
import com.smsmode.pricing.enumeration.CancellationTimingTypeEnum;
import com.smsmode.pricing.enumeration.PostingTimingEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
@Data
public class CancellationPolicyPatchResource {
    private String name;
    private String description;
    private CancellationPolicyTypeEnum type;
    private CancellationTimingTypeEnum timingType;
    private ResourceRefEmbeddable postingAccount;
    private PostingTimingEnum postingTiming;
    private Integer hours;
    private BigDecimal amount;
    private Boolean enabled;
}
