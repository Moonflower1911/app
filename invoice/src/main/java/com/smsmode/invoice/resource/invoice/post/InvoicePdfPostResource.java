package com.smsmode.invoice.resource.invoice.post;

import com.smsmode.invoice.embeddable.ContactEmbeddable;
import com.smsmode.invoice.enumeration.BookingStatusEnum;
import com.smsmode.invoice.enumeration.BookingTypeEnum;
import com.smsmode.invoice.resource.booking.common.StayResource;
import com.smsmode.invoice.resource.booking.get.BookingRefGetResource;
import com.smsmode.invoice.resource.booking.get.FeeGetResource;
import com.smsmode.invoice.resource.common.AuditGetResource;
import lombok.Data;
import java.util.List;

@Data
public class InvoicePdfPostResource {

    //--Booking--
    private String id;
    private String reference;
    private BookingTypeEnum type;
    private StayResource stay;
    private BookingStatusEnum status;
    private BookingRefGetResource parent;
    private List<InvoicePdfPostResource> items;
    private List<FeeGetResource> fees;
    private String notes;
    private ContactEmbeddable contact;
    private AuditGetResource audit;
}