package com.smsmode.guest.resource.iddocument;

import com.smsmode.guest.enumeration.IdDocumentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IdentityDocumentPostResource {
    @NotNull
    private IdDocumentTypeEnum type;
    @NotBlank
    private String value;
    @NotNull
    private LocalDate expirationDate;
    @NotBlank
    private String partyId;
}
