package com.smsmode.guest.resource.guest;

import com.smsmode.guest.embeddable.AddressEmbeddable;
import com.smsmode.guest.embeddable.ContactEmbeddable;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.resource.common.AuditGetResource;
import com.smsmode.guest.resource.segment.SegmentItemGetResource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PartyItemGetResource {
    private String id;
    private String name;
    private String firstName; 
    private String lastName;
    private PartyTypeEnum type;
    private ContactEmbeddable contact;
    private AddressEmbeddable address;
    private LocalDate birthDate;
    private boolean withIdentityDocument;
    private SegmentItemGetResource segment;
    private AuditGetResource audit;
}