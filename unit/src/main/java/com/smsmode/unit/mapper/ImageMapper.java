/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.model.ImageModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.image.ImageGetResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ImageMapper {

    @Mappings({@Mapping(source = "media.uuid", target = "uuid")})
    public abstract ImageGetResource modelToImageGetResource(ImageModel image);

    @AfterMapping
    public void afterModelToImageGetResource(ImageModel image, @MappingTarget ImageGetResource imageGetResource) {
        imageGetResource.setAudit(this.modelToAuditResource(image));
    }

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

}
