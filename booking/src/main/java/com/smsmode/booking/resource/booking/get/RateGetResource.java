/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.resource.booking.get;

import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Aug 2025</p>
 */
@Data
public class RateGetResource {
    private BigDecimal unitPriceExclTax;
    private BigDecimal unitPriceInclTax;
    private BigDecimal quantity;
    private BigDecimal vatPercentage;
    private BigDecimal vatAmount;
    private BigDecimal amountExclTax;
    private BigDecimal amountInclTax;
}
