/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.charge;

import com.smsmode.pricing.embeddable.ResourceRefEmbeddable;
import com.smsmode.pricing.enumeration.ChargingBasisEnum;
import com.smsmode.pricing.enumeration.PostingTimingEnum;
import com.smsmode.pricing.validator.UniqueChargeCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
@Getter
@Setter
public class ChargePostResource {
    @NotBlank
    private String name;
    @NotBlank
    @UniqueChargeCode
    private String code;
    private String description;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private ResourceRefEmbeddable postingAccount;
    private PostingTimingEnum postingTiming = PostingTimingEnum.NIGHTLY;
    @NotNull
    private ChargingBasisEnum chargingBasis;
    private boolean extraAllowed;
    private boolean packageAllowed;
    private boolean excludePrice = false;
    private boolean enabled = true;
}
