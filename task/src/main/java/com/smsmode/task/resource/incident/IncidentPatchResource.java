package com.smsmode.task.resource.incident;

import com.smsmode.task.embeddable.RentalEmbeddable;
import com.smsmode.task.embeddable.UserRefEmbeddable;
import com.smsmode.task.resource.category.CategoryItemGetResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class IncidentPatchResource {
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

    @NotEmpty
    private Set<CategoryItemGetResource> categories;
}
