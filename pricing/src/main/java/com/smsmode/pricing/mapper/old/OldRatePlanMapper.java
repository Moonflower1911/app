package com.smsmode.pricing.mapper.old;

import com.smsmode.pricing.model.old.OldRatePlanModel;
import com.smsmode.pricing.resource.old.common.AuditGetResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPostResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for RatePlan entities and resources.
 */
@Mapper(componentModel = "spring")
public abstract class OldRatePlanMapper {

    /**
     * Maps RatePlanPostResource to RatePlanModel for creation.
     */
    @Mapping(source = "unitId", target = "unit.id")
    public abstract OldRatePlanModel postResourceToModel(RatePlanPostResource ratePlanPostResource);

    /**
     * Maps RatePlanModel to RatePlanGetResource for response.
     */
    @Mapping(target = "audit", source = ".")
    public abstract RatePlanGetResource modelToGetResource(OldRatePlanModel oldRatePlanModel);


    public abstract AuditGetResource modelToAuditResource(OldRatePlanModel oldRatePlanModel);

    /**
     * Updates existing RatePlanModel from RatePlanPostResource for update operations.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "unit", ignore = true)
    public abstract void updateModelFromPatchResource(RatePlanPatchResource ratePlanPatchResource, @MappingTarget OldRatePlanModel oldRatePlanModel);
}