package com.smsmode.booking.controller.impl;

import com.smsmode.booking.controller.BookingController;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.resource.booking.get.BookingGetResource;
import com.smsmode.booking.resource.booking.get.BookingItemGetResource;
import com.smsmode.booking.resource.booking.patch.BookingPatchResource;
import com.smsmode.booking.resource.booking.post.BookingItemPostResource;
import com.smsmode.booking.resource.booking.post.BookingPostResource;
import com.smsmode.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class BookingControllerImpl implements BookingController {

    private final BookingService bookingService;

    @Override
    public ResponseEntity<Page<BookingItemGetResource>> getAllByPage(
            String search,
            BookingStatusEnum[] statuses,
            BookingTypeEnum[] types,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
            Pageable pageable
    ) {
        return bookingService.retrieveAllByPage(search, statuses, types, checkinDate, checkoutDate, pageable);
    }

    @Override
    public ResponseEntity<BookingGetResource> getById(String bookingId) {
        return bookingService.retrieveById(bookingId);
    }

    @Override
    public ResponseEntity<BookingGetResource> post(BookingPostResource bookingPostResource) {
        return bookingService.create(bookingPostResource);
    }

    @Override
    public ResponseEntity<BookingGetResource> postItem(String bookingId, BookingItemPostResource bookingItemPostResource) {
        return bookingService.addBooking(bookingId, bookingItemPostResource);
    }

    @Override
    public ResponseEntity<BookingGetResource> patchById(String bookingId, BookingPatchResource bookingPatchResource) {
        return bookingService.updateById(bookingId, bookingPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String bookingId) {
        return bookingService.removeById(bookingId);
    }

/*    @Override
    public ResponseEntity<BookingGetResource> createBooking(BookingPostResource bookingPostResource) {
        return bookingService.create(bookingPostResource);
    }

    @Override
    public ResponseEntity<BookingGetResource> addItemToBooking(String bookingId, BookingItemPostResource bookingItemPostResource) {
        return bookingService.addItem(bookingId, bookingItemPostResource);
    }

    @Override
    public ResponseEntity<BookingGetResource> getBookingById(String bookingId) {
        return bookingService.retrieveById(bookingId);
    }

    @Override
    public ResponseEntity<Page<BookingGetResource>> getDraftGroupBookings(Pageable pageable) {
        return bookingService.retrieveDraftGroupBookings(pageable);
    }

    @Override
    public ResponseEntity<Void> deleteBooking(String bookingId) {
        return bookingService.deleteById(bookingId);
    }

    @Override
    public ResponseEntity<BookingGetResource> updateBooking(String bookingId, BookingPatchResource bookingPatchResource) {
        return bookingService.updateBooking(bookingId, bookingPatchResource);
    }*/
}