package com.smsmode.invoice.resource.booking.get;

import com.smsmode.invoice.embeddable.FeeOccupancyEmbeddable;
import com.smsmode.invoice.embeddable.FeeRefEmbeddable;
import com.smsmode.invoice.enumeration.FeeModalityEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeGetResource {
    private String id;
    private BigDecimal price;
    private Integer quantity;
    private FeeRefEmbeddable feeRef;
    private FeeModalityEnum modality;
    private FeeOccupancyEmbeddable occupancy;
    private RateGetResource rate;
}
