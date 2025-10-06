package com.smsmode.guest.resource.guest;

import com.smsmode.guest.embeddable.AddressEmbeddable;
import com.smsmode.guest.embeddable.ContactEmbeddable;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PartyPostResource {

    private String name;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @NotNull
    private PartyTypeEnum type;

    @Valid
    private ContactEmbeddable contact;

    @Valid
    private AddressEmbeddable address;

    @Valid
    private GuestIdDocumentPostResource identityDocument;

    @Valid
    private String segmentId;
}