package com.smsmode.booking.resource.booking.get;

import com.smsmode.booking.embeddable.ContactEmbeddable;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.resource.common.AuditGetResource;
import com.smsmode.booking.resource.common.StayResource;
import com.smsmode.booking.resource.fee.get.FeeGetResource;
import lombok.Data;

import java.util.List;

@Data
public class BookingGetResource {
    private String id;
    private String reference;
    private BookingTypeEnum type;
    private StayResource stay;
    private BookingStatusEnum status;
    private BookingRefGetResource parent;
    private List<BookingGetResource> items;
    private List<FeeGetResource> fees;
    private String notes;
    private ContactEmbeddable contact;
    private AuditGetResource audit;
}
