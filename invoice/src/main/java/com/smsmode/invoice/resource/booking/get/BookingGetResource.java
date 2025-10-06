package com.smsmode.invoice.resource.booking.get;

import com.smsmode.invoice.embeddable.ContactEmbeddable;
import com.smsmode.invoice.enumeration.BookingStatusEnum;
import com.smsmode.invoice.enumeration.BookingTypeEnum;
import com.smsmode.invoice.resource.booking.common.StayResource;
import com.smsmode.invoice.resource.common.AuditGetResource;
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