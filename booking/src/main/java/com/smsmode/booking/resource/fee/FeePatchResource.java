/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.resource.fee;

import com.smsmode.booking.embeddable.FeeOccupancyEmbeddable;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
@Data
public class FeePatchResource {
    private BigDecimal price;
    private Integer quantity;
    private FeeOccupancyEmbeddable occupancy;
}
