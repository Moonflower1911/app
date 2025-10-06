/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.service.impl;

import com.smsmode.booking.dao.service.BookingDaoService;
import com.smsmode.booking.service.InternalBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InternalBookingServiceImpl implements InternalBookingService {
    private final BookingDaoService bookingDaoService;

    @Override
    public ResponseEntity<List<String>> retrieveBookedUnitIds(LocalDate checkinDate, LocalDate checkoutDate, boolean strict) {
        return ResponseEntity.ok(bookingDaoService.findBookedUnitIds(checkinDate, checkoutDate, strict));
    }
}
