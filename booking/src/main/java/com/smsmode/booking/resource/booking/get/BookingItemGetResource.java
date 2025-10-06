package com.smsmode.booking.resource.booking.get;

import com.smsmode.booking.embeddable.ContactEmbeddable;
import com.smsmode.booking.embeddable.UnitRefEmbeddable;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.resource.common.AuditGetResource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingItemGetResource {
    private String id;
    private String reference;
    private BookingTypeEnum type;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private UnitRefEmbeddable unitRef;
    private UnitRefEmbeddable roomRef;
    private BookingStatusEnum status;
    private ContactEmbeddable contact;
    private BookingRefGetResource parent;
    private AuditGetResource audit;
}