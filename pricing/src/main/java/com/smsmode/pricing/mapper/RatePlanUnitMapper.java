/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Sep 2025</p>
 */

import com.smsmode.pricing.model.RatePlanUnitModel;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitGetResource;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RatePlanUnitMapper {

    public abstract RatePlanUnitGetResource modelToGetResource(RatePlanUnitModel ratePlanUnitModel);

}
