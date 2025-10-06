/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.guestratestrategy;

import com.smsmode.pricing.embeddable.AgeBucketEmbeddable;
import com.smsmode.pricing.enumeration.AmountTypeEnum;
import com.smsmode.pricing.enumeration.GuestTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
@Data
public class GuestRateRulePostResource {

    // ID is optional: null for CREATE, provided for UPDATE
    private String id;

    @NotNull(message = "Guest count is required")
    @Min(value = 1, message = "Guest count must be at least 1")
    private int guestThreshold;

    @NotNull(message = "Guest type is required")
    private GuestTypeEnum guestType;

    @Valid
    private AgeBucketEmbeddable ageBucket;

    @NotNull(message = "Amount type is required")
    private AmountTypeEnum amountType;

    @NotNull(message = "Value is required")
    @PositiveOrZero(message = "Value must be positive or zero")
    private BigDecimal value;

}
