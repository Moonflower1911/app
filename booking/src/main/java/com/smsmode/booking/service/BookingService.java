package com.smsmode.booking.service;


import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.resource.booking.get.BookingGetResource;
import com.smsmode.booking.resource.booking.get.BookingItemGetResource;
import com.smsmode.booking.resource.booking.patch.BookingPatchResource;
import com.smsmode.booking.resource.booking.post.BookingItemPostResource;
import com.smsmode.booking.resource.booking.post.BookingPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface BookingService {

    ResponseEntity<Page<BookingItemGetResource>> retrieveAllByPage(
            String search,
            BookingStatusEnum[] statuses,
            BookingTypeEnum[] types,
            LocalDate checkinDate,
            LocalDate checkoutDate,
            Pageable pageable
    );

    ResponseEntity<BookingGetResource> retrieveById(String bookingId);

    ResponseEntity<BookingGetResource> create(BookingPostResource bookingPostResource);

    ResponseEntity<BookingGetResource> addBooking(String bookingId, BookingItemPostResource bookingItemPostResource);

    ResponseEntity<BookingGetResource> updateById(String bookingId, BookingPatchResource bookingPatchResource);

    ResponseEntity<Void> removeById(String bookingId);

}