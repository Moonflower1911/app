package com.smsmode.uaa.resource.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountPostResource {

    @NotBlank
    @Size(max = 50)
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    @Valid
    private ManagerResource manager;
}
