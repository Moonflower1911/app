package com.smsmode.media.mapper;

import com.smsmode.media.model.MediaModel;
import com.smsmode.media.model.base.AbstractBaseModel;
import com.smsmode.media.resource.common.AuditGetResource;
import com.smsmode.media.resource.media.MediaGetResource;
import com.smsmode.media.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for Media entity and resources.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class MediaMapper {

    /**
     * Maps MediaModel to MediaGetResource for retrieval.
     */
    public abstract MediaGetResource modelToGetResource(MediaModel mediaModel);

    /**
     * Maps AbstractBaseModel to AuditGetResource for audit information.
     */
    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    /**
     * After mapping method to set audit information.
     */
    @AfterMapping
    public void afterModelToGetResource(MediaModel mediaModel, @MappingTarget MediaGetResource mediaGetResource) {
        mediaGetResource.setAudit(this.modelToAuditResource(mediaModel));
    }
}