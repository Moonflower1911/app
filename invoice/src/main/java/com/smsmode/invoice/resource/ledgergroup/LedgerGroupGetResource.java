package com.smsmode.invoice.resource.ledgergroup;

import com.smsmode.invoice.resource.common.AuditGetResource;
import lombok.Data;

import java.util.List;

@Data
public class LedgerGroupGetResource {
    private String id;
    private String name;
    private boolean enabled;
    private String description;
    private Long subledgerCount;
    private AuditGetResource audit;
    private LedgerGroupParentResource parent;
    private List<LedgerGroupGetResource> subLedgers;
}
