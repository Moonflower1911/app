package com.smsmode.invoice.resource.accountclass;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountClassPostResource {

    @NotBlank
    private String name;

    private String description;
    private Boolean enabled;
}
