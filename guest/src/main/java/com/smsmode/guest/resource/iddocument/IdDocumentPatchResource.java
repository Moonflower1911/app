package com.smsmode.guest.resource.iddocument;

import com.smsmode.guest.enumeration.IdDocumentTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IdDocumentPatchResource {
    private IdDocumentTypeEnum type;
    private String value;
    private LocalDate expirationDate;
}