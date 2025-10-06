package com.smsmode.pricing.resource.old.fee;

import com.smsmode.pricing.embeddable.UnitRefEmbeddable;
import com.smsmode.pricing.enumeration.FeeModalityEnum;
import com.smsmode.pricing.resource.old.common.AuditGetResource;
import com.smsmode.pricing.resource.old.common.additionalguestfee.AdditionalGuestFeeGetResource;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FeeGetResource {
    private String id;
    private String name;
    private BigDecimal amount;
    private FeeModalityEnum modality;
    private String description;
    private boolean active;
    private boolean required;
    private UnitRefEmbeddable unit;
    private AuditGetResource audit;
    private List<AdditionalGuestFeeGetResource> additionalGuestPrices;
}
