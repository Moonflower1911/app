/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.service.impl;

import com.smsmode.booking.dao.service.BookingDaoService;
import com.smsmode.booking.dao.specification.BookingSpecification;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.resource.kpis.TotalCountGetResource;
import com.smsmode.booking.service.KpiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 13 Aug 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KpiServiceImpl implements KpiService {

    private final BookingDaoService bookingDaoService;

    //checkins
    @Override
    public ResponseEntity<TotalCountGetResource> calculateCheckins() {

        long checkInsToday = bookingDaoService.countBy(
                BookingSpecification.withStatusesIn(
                        Stream.of(BookingStatusEnum.CONFIRMED, BookingStatusEnum.CHECKED_IN).toArray(BookingStatusEnum[]::new)
                ).and(BookingSpecification.withCheckinDateLessThanOrEqual(LocalDate.now())).and(
                        BookingSpecification.withTypeEqual(BookingTypeEnum.SINGLE)
                        )
        );


        long alreadyCheckedIn = bookingDaoService.countBy(
                BookingSpecification.withStatusesIn(
                        Stream.of(BookingStatusEnum.CHECKED_IN).toArray(BookingStatusEnum[]::new)
                ).and(BookingSpecification.withCheckedInDateEqual(LocalDate.now())).and(
                        BookingSpecification.withTypeEqual(BookingTypeEnum.SINGLE)
                )
        );
        TotalCountGetResource totalCountGetResource = new TotalCountGetResource();

        totalCountGetResource.setCountCheckIn(checkInsToday);
        totalCountGetResource.setTotalCountCheckIn(alreadyCheckedIn);
        return ResponseEntity.ok(totalCountGetResource);
    }

    //checkout
    @Override
    public ResponseEntity<TotalCountGetResource> calculateCheckouts() {
        //all checkOuts
        long checkOutToday = bookingDaoService.countBy(BookingSpecification.withCheckOutDateEqual(LocalDate.now()));
        //already checkedOut
        long alreadyCheckedOut = bookingDaoService.countBy(BookingSpecification.withStatusesIn(
                Stream.of(BookingStatusEnum.CHECKED_OUT).toArray(BookingStatusEnum[]::new)).and(
                        BookingSpecification.withCheckOutDateEqual(LocalDate.now())
                )
        );
        TotalCountGetResource totalCountGetResource = new TotalCountGetResource();

        totalCountGetResource.setCountCheckOut(alreadyCheckedOut);
        totalCountGetResource.setTotalCountCheckOut(checkOutToday);
        return ResponseEntity.ok(totalCountGetResource);
    }

    //in-house
    @Override
    public ResponseEntity<TotalCountGetResource> calculateInHouse() {
        long totalInHouse = bookingDaoService.countBy(BookingSpecification.withStatusesIn(
                        Stream.of(BookingStatusEnum.CHECKED_IN).toArray(BookingStatusEnum[]::new)).and(
                        BookingSpecification.withCheckinBeforeOrEqualAndCheckoutAfter(LocalDate.now()).and(
                                BookingSpecification.withTypeEqual(BookingTypeEnum.SINGLE)
                        ))
        );
        TotalCountGetResource totalCountGetResource = new TotalCountGetResource();

        totalCountGetResource.setInHouse(totalInHouse);
        return ResponseEntity.ok(totalCountGetResource);
    }

    @Override
    public ResponseEntity<TotalCountGetResource> calculateBooking() {

        long totalBookingToday = bookingDaoService.countBy(BookingSpecification.withStatusesIn(
                Stream.of(BookingStatusEnum.CHECKED_IN, BookingStatusEnum.CONFIRMED).toArray(BookingStatusEnum[]::new)).and(
                BookingSpecification.withDateCreationToday(LocalDate.now()).and(
                        BookingSpecification.withTypeEqual(BookingTypeEnum.SINGLE)
                ))
        );
        TotalCountGetResource totalCountGetResource = new TotalCountGetResource();
        totalCountGetResource.setBooking(totalBookingToday);
        return ResponseEntity.ok(totalCountGetResource);
    }
}
