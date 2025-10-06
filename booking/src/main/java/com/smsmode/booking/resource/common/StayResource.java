/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.resource.common;

import com.smsmode.booking.embeddable.BookingOccupancyEmbeddable;
import com.smsmode.booking.embeddable.RefEmbeddable;
import com.smsmode.booking.resource.booking.get.RateGetResource;
import lombok.Data;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Aug 2025</p>
 */
@Data
public class StayResource {
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private RefEmbeddable unitRef;
    private RefEmbeddable roomRef;
    private RateGetResource totalPrice;
    private BookingOccupancyEmbeddable occupancy;
}
