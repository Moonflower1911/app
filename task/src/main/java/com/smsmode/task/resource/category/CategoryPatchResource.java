package com.smsmode.task.resource.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryPatchResource {

    @NotEmpty
    private String name;
}