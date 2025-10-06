package com.smsmode.invoice.resource.postingaccount;

import com.smsmode.invoice.resource.common.AuditGetResource;
import com.smsmode.invoice.resource.common.ResourceRefGetResource;
import lombok.Data;

@Data
public class PostingAccountsGetResource {
    private String id;
    private String name;
    private String code;
    private String description;
    private boolean enabled;
    private ResourceRefGetResource ledgerGroup;
    private ResourceRefGetResource subLedgerGroup;
    private ResourceRefGetResource vatRule;
    private AuditGetResource audit;
//    private ResourceRefGetResource accountClass;
//    private ResourceRefGetResource vatPostingAccount;
}
