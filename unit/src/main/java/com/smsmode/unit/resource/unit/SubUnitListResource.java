package com.smsmode.unit.resource.unit;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * Resource used specifically for attaching or creating subunits under a multi-unit.
 */
@Data
public class SubUnitListResource {

    @NotEmpty(message = "At least one subunit must be provided")
    @Valid
    private List<SubUnitResource> subUnits;
}
