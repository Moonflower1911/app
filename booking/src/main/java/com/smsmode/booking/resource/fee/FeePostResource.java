/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.resource.fee;

import com.smsmode.booking.embeddable.FeeOccupancyEmbeddable;
import com.smsmode.booking.embeddable.FeeRefEmbeddable;
import com.smsmode.booking.enumeration.FeeModalityEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
@Data
public class FeePostResource {
    private FeeRefEmbeddable feeRef;
    private BigDecimal price;
    private BigDecimal vatPercentage;
    private Integer quantity;
    private String bookingId;
    private FeeModalityEnum modality;
    private FeeOccupancyEmbeddable occupancy;
}
