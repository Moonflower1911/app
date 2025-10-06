/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.ChargeModel;
import com.smsmode.pricing.resource.charge.ChargeItemGetResource;
import com.smsmode.pricing.resource.charge.ChargePatchResource;
import com.smsmode.pricing.resource.charge.ChargePostResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ChargeMapper {

    public abstract ChargeItemGetResource modelToItemGetResource(ChargeModel chargeModel);

    public abstract ChargeModel postResourceToModel(ChargePostResource chargePostResource);

    public abstract ChargeModel patchResourceToModel(ChargePatchResource chargePatchResource, @MappingTarget ChargeModel chargeModel);
}
