/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.controller;

import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.resource.booking.get.BookingGetResource;
import com.smsmode.booking.resource.booking.get.BookingItemGetResource;
import com.smsmode.booking.resource.booking.patch.BookingPatchResource;
import com.smsmode.booking.resource.booking.post.BookingItemPostResource;
import com.smsmode.booking.resource.booking.post.BookingPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jul 2025</p>
 */

@RequestMapping("/bookings")
public interface BookingController {

    @GetMapping
    ResponseEntity<Page<BookingItemGetResource>> getAllByPage(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BookingStatusEnum[] statuses,
            @RequestParam(required = false) BookingTypeEnum[] types,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate,
            Pageable pageable
    );

    @GetMapping("{bookingId}")
    ResponseEntity<BookingGetResource> getById(@PathVariable("bookingId") String bookingId);

    @PostMapping
    ResponseEntity<BookingGetResource> post(@RequestBody @Valid BookingPostResource bookingPostResource);

    @PostMapping("{bookingId}")
    ResponseEntity<BookingGetResource> postItem(@PathVariable String bookingId, @RequestBody @Valid BookingItemPostResource bookingItemPostResource);

    @PatchMapping("{bookingId}")
    ResponseEntity<BookingGetResource> patchById(@PathVariable String bookingId, @Valid @RequestBody BookingPatchResource bookingPatchResource);

    @DeleteMapping("{bookingId}")
    ResponseEntity<Void> deleteById(@PathVariable String bookingId);

}

