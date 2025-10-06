package com.smsmode.uaa.resource.account;

import com.smsmode.uaa.resource.common.AuditGetResource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountItemGetResource {

    private Integer id;
    private String name;
    private boolean enabled;
    private LocalDate startDate;
    private LocalDate endDate;

    private AuditGetResource audit;
}
