package com.smsmode.invoice.resource.postingaccount;

import lombok.Data;

@Data
public class PostingAccountsPatchResource {
    private String name;
    private String description;
    private Boolean enabled;
    private String ledgerGroupId;
    private String subLedgerGroupId;
    private String vatRuleId;
//    private String accountClassId;
//    private String vatPostingAccountId;
}
