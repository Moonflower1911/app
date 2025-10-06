package com.smsmode.task.resource.category;

import com.smsmode.task.resource.common.AuditGetResource;
import lombok.Data;

@Data
public class CategoryItemGetResource {
    private String id;
    private String name;
    private AuditGetResource audit;
}
