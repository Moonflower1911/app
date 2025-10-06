/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 04 Sep 2025</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRateGetResource {
    private String unitId;
    private String unitName;
    private List<DailyRateGetResource> dailyRates;
}
