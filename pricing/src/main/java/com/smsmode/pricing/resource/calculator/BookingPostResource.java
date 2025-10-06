/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.calculator;

import com.smsmode.pricing.resource.old.calculate.GuestsPostResource;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Sep 2025</p>
 */
@Data
public class BookingPostResource {
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private GuestsPostResource guests;
    private Set<String> units;
}
