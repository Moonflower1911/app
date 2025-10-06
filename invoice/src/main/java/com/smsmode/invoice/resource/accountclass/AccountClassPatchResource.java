package com.smsmode.invoice.resource.accountclass;

import lombok.Data;

@Data
public class AccountClassPatchResource {
    private String name;
    private String description;
    private Boolean enabled;
}
