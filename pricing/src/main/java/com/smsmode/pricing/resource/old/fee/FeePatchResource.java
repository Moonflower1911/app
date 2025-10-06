package com.smsmode.pricing.resource.old.fee;

import com.smsmode.pricing.enumeration.FeeModalityEnum;
import com.smsmode.pricing.resource.old.common.additionalguestfee.AdditionalGuestFeePostResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FeePatchResource {

    @NotBlank
    private String name;

    @DecimalMin("0.0")
    private BigDecimal amount;


    private FeeModalityEnum modality;

    private String description;

    private Boolean active;

    private Boolean required;

    @Valid
    private List<@Valid AdditionalGuestFeePostResource> additionalGuestPrices;
}
