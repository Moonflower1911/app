/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.resource.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanItemGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPostResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 02 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RatePlanMapper {

    public abstract RatePlanModel postResourceToModel(RatePlanPostResource ratePlanPostResource);

    @Mapping(target = "restrictions.minLos", source = "minLos")
    @Mapping(target = "restrictions.maxLos", source = "maxLos")
    @Mapping(target = "restrictions.minLead", source = "minLead")
    @Mapping(target = "restrictions.maxLead", source = "maxLead")
    public abstract RatePlanGetResource modelToGetResource(RatePlanModel ratePlanModel);

    @Mapping(target = "units", ignore = true)
    @Mapping(target = "cancellationPolicy", ignore = true)
    @Mapping(target = "minLos", ignore = true)
    @Mapping(target = "maxLos", ignore = true)
    @Mapping(target = "minLead", ignore = true)
    @Mapping(target = "maxLead", ignore = true)
    public abstract RatePlanModel patchResourceToModel(RatePlanPatchResource ratePlanPatchResource, @MappingTarget RatePlanModel ratePlanModel);

    public abstract RatePlanItemGetResource modelToItemGetResource(RatePlanModel ratePlanModel);
}
