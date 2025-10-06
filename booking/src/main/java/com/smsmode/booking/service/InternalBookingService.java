/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
public interface InternalBookingService {

    ResponseEntity<List<String>> retrieveBookedUnitIds(LocalDate checkinDate, LocalDate checkoutDate, boolean strict);

}
