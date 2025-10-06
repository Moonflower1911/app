package com.smsmode.invoice.resource.booking.common;

import com.smsmode.invoice.embeddable.BookingOccupancyEmbeddable;
import com.smsmode.invoice.embeddable.RefEmbeddable;
import com.smsmode.invoice.resource.booking.get.RateGetResource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StayResource {
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private RefEmbeddable unitRef;
    private RefEmbeddable roomRef;
    private RateGetResource totalPrice;
    private BookingOccupancyEmbeddable occupancy;
}
