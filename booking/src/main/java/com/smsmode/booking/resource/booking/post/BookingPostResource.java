package com.smsmode.booking.resource.booking.post;

import lombok.Data;

import java.util.List;

@Data
public class BookingPostResource {
    private List<BookingItemPostResource> items;
}
