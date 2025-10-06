package com.smsmode.uaa.resource.account;

import lombok.Data;

import java.time.LocalDate;


@Data
public class AccountPatchResource {

    private String name;
    private Boolean enabled;
    private LocalDate startDate;
    private LocalDate endDate;
}
