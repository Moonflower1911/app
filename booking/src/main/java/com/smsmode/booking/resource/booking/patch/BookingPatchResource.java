package com.smsmode.booking.resource.booking.patch;

import com.smsmode.booking.embeddable.ContactEmbeddable;
import com.smsmode.booking.embeddable.RefEmbeddable;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.resource.common.RefResource;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Resource for updating booking information (status confirmation or item updates)
 */
@Data
public class BookingPatchResource {
    private String notes;
    private BigDecimal nightlyRate;
    private BookingStatusEnum status;
    private Boolean applyStatusToAll;
    private ContactEmbeddable contact;
    private RefEmbeddable roomRef;
}