/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.charge;

import com.smsmode.pricing.embeddable.ResourceRefEmbeddable;
import com.smsmode.pricing.enumeration.ChargingBasisEnum;
import com.smsmode.pricing.enumeration.PostingTimingEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
@Data
public class ChargeItemGetResource {
    private String id;
    private String name;
    private String code;
    private String description;
    private BigDecimal amount;
    private ResourceRefEmbeddable postingAccount;
    private PostingTimingEnum postingTiming;
    private ChargingBasisEnum chargingBasis;
    private boolean extraAllowed;
    private boolean packageAllowed;
    private boolean excludePrice;
    private boolean enabled;
}
