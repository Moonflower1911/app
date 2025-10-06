/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Sep 2025</p>
 */
@Slf4j
public class DateUtils {
    public static int calculateNights(LocalDate checkinDate, LocalDate checkoutDate) {
        log.info("[DateUtils] calculateNights called with checkin={} checkout={}", checkinDate, checkoutDate);

        if (checkinDate == null || checkoutDate == null) {
            log.error("[DateUtils] Null date(s) detected -> checkin={} checkout={}", checkinDate, checkoutDate);
            throw new IllegalArgumentException("Check-in and check-out dates must not be null");
        }

        if (!checkoutDate.isAfter(checkinDate)) {
            log.error("[DateUtils] Invalid date order -> checkout={} is not after checkin={}", checkoutDate, checkinDate);
            throw new IllegalArgumentException("Checkout date must be after check-in date");
        }

        int nights = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        log.debug("[DateUtils] Nights between {} and {} = {}", checkinDate, checkoutDate, nights);
        return nights;
    }

    public static int calculateDays(LocalDate startDate, LocalDate endDate) {
        log.info("[DateUtils] calculateDays called with startDate={} endDate={}", startDate, endDate);
        int days = DateUtils.calculateNights(startDate, endDate) + 1;
        log.debug("[DateUtils] Days between {} and {} (inclusive) = {}", startDate, endDate, days);
        return days;
    }

    public static List<LocalDate> getDatesBetween(LocalDate checkinDate, LocalDate checkoutDate) {
        log.info("[DateUtils] getDatesBetween called with checkin={} checkout={}", checkinDate, checkoutDate);

        if (checkinDate == null || checkoutDate == null) {
            log.error("[DateUtils] Null date(s) detected -> checkin={} checkout={}", checkinDate, checkoutDate);
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (!checkoutDate.isAfter(checkinDate)) {
            log.error("[DateUtils] Invalid date range -> checkout={} is not after checkin={}", checkoutDate, checkinDate);
            throw new IllegalArgumentException("Checkout date must be after checkin date");
        }

        List<LocalDate> dates = checkinDate.datesUntil(checkoutDate).toList();
        log.debug("[DateUtils] Generated {} dates between {} and {}", dates.size(), checkinDate, checkoutDate);
        return dates;
    }
}
