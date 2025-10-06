package com.smsmode.guest.resource.iddocument;

import com.smsmode.guest.enumeration.IdDocumentTypeEnum;
import com.smsmode.guest.resource.common.AuditGetResource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IdentityDocumentItemGetResource {
    private String id;
    private IdDocumentTypeEnum type;
    private String value;
    private LocalDate expirationDate;
    private boolean fileProvided;
    private AuditGetResource audit;
    private String mediaId;
}