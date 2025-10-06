/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 04 Sep 2025</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyRateGetResource {
    private LocalDate date;
    private BigDecimal singleAdult;
    private BigDecimal doubleAdult;
    private BigDecimal extraAdult;
    private BigDecimal extraChild;
}
