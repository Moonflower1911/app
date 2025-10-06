package com.smsmode.pricing.resource.old.rateplan;

import com.smsmode.pricing.embeddable.SegmentRefEmbeddable;
import com.smsmode.pricing.embeddable.UnitRefEmbeddable;
import com.smsmode.pricing.resource.rateplan.RestrictionsPatchResource;
import lombok.Data;

import java.util.Set;

/**
 * Resource for creating and updating rate plans.
 */
@Data
public class RatePlanPatchResource {
    private String name;
    private Set<SegmentRefEmbeddable> segments;
    private Boolean enabled = false;
    private UnitRefEmbeddable unit;
}