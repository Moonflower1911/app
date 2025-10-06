package com.smsmode.uaa.resource.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManagerResource {
    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    private String mobile;
}
