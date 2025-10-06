package com.smsmode.guest.mapper;

import com.smsmode.guest.dao.service.DocumentDaoService;
import com.smsmode.guest.dao.specification.DocumentSpecification;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.model.PartyModel;
import com.smsmode.guest.model.SegmentModel;
import com.smsmode.guest.model.base.AbstractBaseModel;
import com.smsmode.guest.resource.common.AuditGetResource;

import com.smsmode.guest.resource.guest.PartyItemGetResource;
import com.smsmode.guest.resource.guest.PartyPatchResource;
import com.smsmode.guest.resource.guest.PartyPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Mapper for Guest entity and resources.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PartyMapper {

    private DocumentDaoService documentDaoService;

    @Autowired
    void setIdentityDocumentDaoService(DocumentDaoService documentDaoService) {
        this.documentDaoService = documentDaoService;
    }

    /**
     * Maps GuestPostResource to GuestModel for creation.
     */
    public abstract PartyModel postResourceToModel(PartyPostResource partyPostResource);

    /**
     * Maps GuestModel to GuestGetResource for retrieval.
     */
    public abstract PartyItemGetResource modelToItemGetResource(PartyModel partyModel);

    @AfterMapping
    public void afterModelToItemGetResource(PartyModel partyModel, @MappingTarget PartyItemGetResource partyItemGetResource) {
        if (partyModel.getType() == PartyTypeEnum.GUEST) {
            partyItemGetResource.setFirstName(partyModel.getFirstName());
            partyItemGetResource.setLastName(partyModel.getLastName());
        } else if (partyModel.getType() == PartyTypeEnum.COMPANY) {
            partyItemGetResource.setName(partyModel.getName());
        }

        partyItemGetResource.setAudit(this.modelToAuditResource(partyModel));
        partyItemGetResource.setWithIdentityDocument(documentDaoService.existsBy(DocumentSpecification.withPartyId(partyItemGetResource.getId())));
    }

    /**
     * Maps GuestPatchResource to GuestModel for partial updates.
     */
    @Mapping(target = "segment", source = "segmentId", qualifiedByName = "segmentIdToSegmentModel")
    public abstract PartyModel patchResourceToModel(PartyPatchResource partyPatchResource, @MappingTarget PartyModel partyModel);


    /**
     * Maps AbstractBaseModel to AuditGetResource for audit information.
     */
    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);



    @Named("segmentIdToSegmentModel")
    protected SegmentModel segmentIdToSegmentModel(String segmentId) {
        if (segmentId == null) return null;
        SegmentModel segment = new SegmentModel();
        segment.setId(segmentId);
        return segment;
    }

}