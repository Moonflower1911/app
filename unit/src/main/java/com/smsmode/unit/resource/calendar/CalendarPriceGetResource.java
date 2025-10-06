/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.calendar;

import com.smsmode.unit.resource.pricing.PricingGetResource;
import com.smsmode.unit.resource.unit.get.UnitRefGetResource;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Jul 2025</p>
 */
@Data
public class CalendarPriceGetResource {

    private UnitRefGetResource unit;
    private Map<LocalDate, PricingGetResource> prices;

}
