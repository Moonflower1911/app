package com.smsmode.pricing.resource.old.common;

import com.smsmode.pricing.resource.old.common.additionalguestfee.AdditionalGuestFeePostResource;
import com.smsmode.pricing.resource.old.common.dayspecificrate.DaySpecificRatePostResource;
import com.smsmode.pricing.validator.old.ValidGuestFees;
import com.smsmode.pricing.validator.old.ValidDaySpecificRates;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
@ValidGuestFees
@ValidDaySpecificRates
public abstract class BaseRateResource {

    @Valid
    private List<@Valid DaySpecificRatePostResource> daySpecificRates;

    @Valid
    private List<@Valid AdditionalGuestFeePostResource> additionalGuestFees;
}
