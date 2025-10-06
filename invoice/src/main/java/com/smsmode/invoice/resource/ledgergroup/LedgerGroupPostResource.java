package com.smsmode.invoice.resource.ledgergroup;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LedgerGroupPostResource {
    @NotBlank
    private String name;

    private String description;

    private Boolean enabled;

    private String parentId;
}
