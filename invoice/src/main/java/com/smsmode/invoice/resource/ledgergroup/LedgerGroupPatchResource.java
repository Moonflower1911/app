package com.smsmode.invoice.resource.ledgergroup;

import lombok.Data;

@Data
public class LedgerGroupPatchResource {
    private String name;
    private String description;
    private Boolean enabled;
    private String parentId;
}
