/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rate;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 05 Sep 2025</p>
 */
@Data
public class RatePostResource {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal singleAdult;
    private BigDecimal doubleAdult;
    private BigDecimal extraAdult;
    private BigDecimal extraChild;
    private String ratePlanId;
    private Set<String> unitIds;
}
