/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.smsmode.invoice.mapper;

import com.smsmode.invoice.model.PaymentModel;
import com.smsmode.invoice.resource.payment.PaymentGetResource;
import com.smsmode.invoice.resource.payment.PaymentPatchResource;
import com.smsmode.invoice.resource.payment.PaymentPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class PaymentMapper {

    @Mapping(target = "id", ignore = true)
    public abstract PaymentModel postResourceToModel(PaymentPostResource paymentPostResource);

    public abstract PaymentGetResource modelToGetResource(PaymentModel paymentModel);

    @Mapping(target = "id", ignore = true)
    public abstract PaymentModel patchResourceToModel(PaymentPatchResource paymentPatchResource, @MappingTarget PaymentModel paymentModel);
}
