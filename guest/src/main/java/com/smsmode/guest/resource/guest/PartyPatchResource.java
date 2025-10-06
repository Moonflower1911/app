package com.smsmode.guest.resource.guest;

import com.smsmode.guest.embeddable.AddressEmbeddable;
import com.smsmode.guest.embeddable.ContactEmbeddable;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for partial Guest updates (PATCH operations).
 * All fields are optional for partial updates.
 */
@Data
public class PartyPatchResource {
    private String firstName;
    private String lastName;

    private String name;
    private String segmentId;

    @Valid
    private ContactEmbeddable contact;

    @Valid
    private AddressEmbeddable address;

    private LocalDate birthDate;
}