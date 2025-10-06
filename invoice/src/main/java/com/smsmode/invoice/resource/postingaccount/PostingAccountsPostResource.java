package com.smsmode.invoice.resource.postingaccount;

import com.smsmode.invoice.validator.UniqueCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostingAccountsPostResource {

    @NotBlank
    private String name;
    @NotBlank
    @UniqueCode
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Code must contain only letters and numbers (no spaces or special characters).")
    private String code;
    private String description;
    private Boolean enabled;
    private String ledgerGroupId;
    private String subLedgerGroupId;
    private String vatRuleId;
//    private String vatPostingAccountId;
//    @NotBlank
//    private String accountClassId;
}
