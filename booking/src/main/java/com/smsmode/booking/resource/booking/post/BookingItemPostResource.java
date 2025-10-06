package com.smsmode.booking.resource.booking.post;

import com.smsmode.booking.embeddable.BookingOccupancyEmbeddable;
import com.smsmode.booking.embeddable.UnitRefEmbeddable;
import com.smsmode.booking.resource.fee.FeePostResource;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookingItemPostResource {
    @Column(name = "checkin_date")
    private LocalDate checkinDate;
    @Column(name = "checkout_date")
    private LocalDate checkoutDate;
    private BigDecimal nightlyRate;
    private BigDecimal vatPercentage;
    private UnitRefEmbeddable unitRef;
    private BookingOccupancyEmbeddable occupancy;
    //fees
    private List<FeePostResource> fees;
}
