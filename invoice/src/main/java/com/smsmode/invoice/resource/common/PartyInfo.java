package com.smsmode.invoice.resource.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PartyInfo {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;
}

