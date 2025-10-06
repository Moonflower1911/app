package com.smsmode.unit.resource.unit;

import lombok.Data;

@Data
public class SubUnitResource {
    private String unitId;
    private String name;
    private Integer priority;
    private Boolean readiness;
}
