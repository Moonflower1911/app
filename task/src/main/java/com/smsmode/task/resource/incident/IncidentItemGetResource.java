package com.smsmode.task.resource.incident;

import com.smsmode.task.embeddable.RentalEmbeddable;
import com.smsmode.task.embeddable.UserRefEmbeddable;
import com.smsmode.task.resource.category.CategoryItemGetResource;
import com.smsmode.task.resource.common.AuditGetResource;
import lombok.Data;

import java.util.Set;

@Data
public class IncidentItemGetResource {
    private String id;

    private String name;

    private UserRefEmbeddable reporter;
    private UserRefEmbeddable reviewer;
    private RentalEmbeddable rental;

    private String severity;
    private String status;
    private String tags;
    private String description;

    private Set<CategoryItemGetResource> categories;

    private AuditGetResource audit;
}
