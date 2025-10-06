package com.smsmode.invoice.resource.accountclass;

import com.smsmode.invoice.resource.common.AuditGetResource;
import lombok.Data;

@Data
public class AccountClassGetResource {
    private String id;
    private String name;
    private String description;
    private boolean enabled;
    private boolean isSystemCritical;
    private AuditGetResource audit;
}
