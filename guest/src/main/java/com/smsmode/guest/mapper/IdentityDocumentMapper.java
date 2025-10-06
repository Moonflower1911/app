/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.mapper;

import com.smsmode.guest.model.DocumentModel;
import com.smsmode.guest.model.base.AbstractBaseModel;
import com.smsmode.guest.resource.common.AuditGetResource;
import com.smsmode.guest.resource.guest.GuestIdDocumentPostResource;
import com.smsmode.guest.resource.iddocument.IdDocumentPatchResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentItemGetResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.util.ObjectUtils;

/**
 * Mapper for IdentificationDocument entity and resources.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class IdentityDocumentMapper {

    /**
     * Maps IdDocumentPostResource to IdentificationDocumentModel for creation.
     */
    @Mapping(target = "party", ignore = true)
    public abstract DocumentModel postResourceToModel(IdentityDocumentPostResource identityDocumentPostResource);

    @Mapping(target = "party", ignore = true)
    public abstract DocumentModel postResourceToModel(GuestIdDocumentPostResource identityDocumentPostResource);

    /**
     * Maps IdentificationDocumentModel to IdDocumentGetResource for retrieval.
     */
    @Mapping(source = "media.id", target = "mediaId")
    public abstract IdentityDocumentItemGetResource modelToItemGetResource(DocumentModel documentModel);

    /**
     * Maps IdDocumentPatchResource to IdentificationDocumentModel for partial updates.
     */
    @Mapping(target = "party", ignore = true)
    public abstract DocumentModel patchResourceToModel(IdDocumentPatchResource idDocumentPatchResource, @MappingTarget DocumentModel idDocumentModel);

    /**
     * Maps AbstractBaseModel to AuditGetResource for audit information.
     */
    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    /**
     * After mapping method to set audit information.
     */
    @AfterMapping
    public void afterModelToItemGetResource(DocumentModel documentModel, @MappingTarget IdentityDocumentItemGetResource identityDocumentItemGetResource) {
        identityDocumentItemGetResource.setFileProvided(!ObjectUtils.isEmpty(documentModel));
        identityDocumentItemGetResource.setAudit(this.modelToAuditResource(documentModel));
    }
}