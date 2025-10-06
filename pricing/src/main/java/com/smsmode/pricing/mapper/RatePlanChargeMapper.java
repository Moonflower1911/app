/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.RatePlanChargeModel;
import com.smsmode.pricing.resource.inclusion.InclusionItemGetResource;
import com.smsmode.pricing.resource.inclusion.InclusionPatchResource;
import com.smsmode.pricing.resource.inclusion.InclusionPostResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RatePlanChargeMapper {

    @Mapping(target = "amount", source = "charge.amount")
    @Mapping(target = "chargingBasis", source = "charge.chargingBasis")
    @Mapping(target = "postingTiming", source = "charge.postingTiming")
    public abstract InclusionItemGetResource modelToItemGetResource(RatePlanChargeModel ratePlanCharge);

    @Mapping(target = "ratePlan", ignore = true)
    @Mapping(target = "charge", ignore = true)
    public abstract RatePlanChargeModel postResourceToModel(InclusionPostResource inclusionPostResource);

    @Mapping(target = "ratePlan", ignore = true)
    @Mapping(target = "charge", ignore = true)
    public abstract RatePlanChargeModel patchResourceToModel(InclusionPatchResource inclusionPatchResource, @MappingTarget RatePlanChargeModel ratePlanChargeModel);
}
