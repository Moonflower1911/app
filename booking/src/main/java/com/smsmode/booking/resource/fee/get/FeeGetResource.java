/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.resource.fee.get;

import com.smsmode.booking.embeddable.FeeOccupancyEmbeddable;
import com.smsmode.booking.embeddable.FeeRefEmbeddable;
import com.smsmode.booking.enumeration.FeeModalityEnum;
import com.smsmode.booking.resource.booking.get.RateGetResource;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
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
