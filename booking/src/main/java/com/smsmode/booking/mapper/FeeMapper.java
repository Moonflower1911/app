/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.mapper;

import com.smsmode.booking.model.FeeModel;
import com.smsmode.booking.resource.fee.FeePatchResource;
import com.smsmode.booking.resource.fee.FeePostResource;
import com.smsmode.booking.resource.fee.get.FeeGetResource;
import com.smsmode.booking.service.impl.BookingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class FeeMapper {

    @Mapping(target = "booking", ignore = true)
    public abstract FeeModel postResourceToModel(FeePostResource feePostResource);

    public abstract FeeGetResource modelToGetResource(FeeModel feeModel);

    @AfterMapping
    public void afterModelToGetResource(FeeModel feeModel, @MappingTarget FeeGetResource feeGetResource) {
        feeGetResource.setRate(BookingServiceImpl.buildRateFromUnitPriceInclTax(feeModel.getPrice(), feeModel.getVatPercentage(), BigDecimal.valueOf(feeModel.getQuantity())));

    }

    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "modality", ignore = true)
    @Mapping(target = "feeRef", ignore = true)
    public abstract FeeModel patchResourceToModel(FeePatchResource feePatchResource, @MappingTarget FeeModel feeModel);

}
