/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.PropertySettingsModel;
import com.smsmode.pricing.resource.settings.PropertySettingsGetResource;
import com.smsmode.pricing.resource.settings.PropertySettingsPostResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PropertySettingsMapper {

    public abstract PropertySettingsModel postResourceToModel(PropertySettingsPostResource propertySettingsPostResource);

    public abstract PropertySettingsModel postResourceToModel(PropertySettingsPostResource propertySettingsPostResource, @MappingTarget PropertySettingsModel propertySettingsModel);

    public abstract PropertySettingsGetResource modelToGetResource(PropertySettingsModel propertySettingsModel);
}
