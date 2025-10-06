/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.resource.kpis;

import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 13 Aug 2025</p>
 */
@Data
public class TotalCountGetResource {
    private Long totalCountCheckIn;
    private Long countCheckIn;

    private Long totalCountCheckOut;
    private Long countCheckOut;

    private Long inHouse;

    private Long Booking;
}
