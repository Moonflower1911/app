package com.smsmode.task.resource.incident;

import com.smsmode.task.embeddable.RentalEmbeddable;
import com.smsmode.task.embeddable.UserRefEmbeddable;
import com.smsmode.task.resource.category.CategoryItemGetResource;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Data
public class IncidentPostResource {

    @NotBlank
    private String name;

    @NotNull
    private UserRefEmbeddable reporter;

    private UserRefEmbeddable reviewer;

    private RentalEmbeddable rental;

    @NotBlank
    private String severity;

    @NotBlank
    private String status;

    private String tags;

    private String description;

    @NotNull
    private Set<CategoryItemGetResource> categories;
}
