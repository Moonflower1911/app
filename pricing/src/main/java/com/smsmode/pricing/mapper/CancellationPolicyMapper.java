/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.CancellationPolicyModel;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyGetResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPatchResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPostResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CancellationPolicyMapper {

    public abstract CancellationPolicyGetResource modelToGetResource(CancellationPolicyModel cancellationPolicyModel);

    public abstract CancellationPolicyModel postResourceToModel(CancellationPolicyPostResource cancellationPolicyPostResource);

    public abstract CancellationPolicyModel patchResourceToModel(CancellationPolicyPatchResource cancellationPolicyPatchResource, @MappingTarget CancellationPolicyModel cancellationPolicyModel);
}
