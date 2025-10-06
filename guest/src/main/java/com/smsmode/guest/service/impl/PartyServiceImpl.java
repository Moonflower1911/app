/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.service.impl;

import com.smsmode.guest.dao.service.DocumentDaoService;
import com.smsmode.guest.dao.service.PartyDaoService;
import com.smsmode.guest.dao.service.SegmentDaoService;
import com.smsmode.guest.dao.specification.DocumentSpecification;
import com.smsmode.guest.dao.specification.PartySpecification;
import com.smsmode.guest.dao.specification.SegmentSpecification;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.mapper.IdentityDocumentMapper;
import com.smsmode.guest.mapper.PartyMapper;
import com.smsmode.guest.model.DocumentModel;
import com.smsmode.guest.model.PartyModel;
import com.smsmode.guest.model.SegmentModel;
import com.smsmode.guest.resource.guest.PartyItemGetResource;
import com.smsmode.guest.resource.guest.PartyPatchResource;
import com.smsmode.guest.resource.guest.PartyPostResource;
import com.smsmode.guest.service.IdentityDocumentService;
import com.smsmode.guest.service.PartyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyMapper partyMapper;
    private final PartyDaoService partyDaoService;
    private final IdentityDocumentService identityDocumentService;
    private final DocumentDaoService documentDaoService;
    private final IdentityDocumentMapper identityDocumentMapper;
    private final SegmentDaoService segmentDaoService;

    @Override
    @Transactional
    public ResponseEntity<PartyItemGetResource> create(PartyPostResource partyPostResource, MultipartFile identityDocumentFile) {
        PartyModel partyModel = partyMapper.postResourceToModel(partyPostResource);
        if (partyPostResource.getType().equals(PartyTypeEnum.COMPANY)) {
            SegmentModel segmentModel = segmentDaoService.findOneBy(SegmentSpecification.withIdEqual(partyPostResource.getSegmentId()));
            partyModel.setSegment(segmentModel);
        }
        partyModel = partyDaoService.save(partyModel);
        DocumentModel documentModel = identityDocumentMapper.postResourceToModel(partyPostResource.getIdentityDocument());
        identityDocumentService.create(partyModel, documentModel, identityDocumentFile);
        return ResponseEntity.created(URI.create("")).body(partyMapper.modelToItemGetResource(partyModel));
    }

    @Override
    public ResponseEntity<Page<PartyItemGetResource>> retrieveAllByPage(String search, PartyTypeEnum type, Pageable pageable) {
        Specification<PartyModel> specification = Specification.where(null);

        if (search != null && !search.isBlank()) {
            specification = specification.and(
                    PartySpecification.withFirstNameLike(search)
                            .or(PartySpecification.withLastNameLike(search))
                            .or(PartySpecification.withEmailLike(search))
            );
        }

        if (type != null) {
            specification = specification.and(PartySpecification.withTypeEqual(type));
        }

        Page<PartyModel> parties = partyDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(parties.map(partyMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<PartyItemGetResource> retrieveById(String guestId) {
        PartyModel guest = partyDaoService.findOneBy(PartySpecification.withIdEqual(guestId));
        return ResponseEntity.ok(partyMapper.modelToItemGetResource(guest));
    }

    @Override
    public ResponseEntity<PartyItemGetResource> updateById(String partyId, PartyPatchResource partyPatchResource) {
        PartyModel existingParty = partyDaoService.findOneBy(PartySpecification.withIdEqual(partyId));

        PartyModel updatedParty = partyMapper.patchResourceToModel(partyPatchResource, existingParty);

        updatedParty = partyDaoService.save(updatedParty);

        return ResponseEntity.ok(partyMapper.modelToItemGetResource(updatedParty));
    }


    @Override
    @Transactional
    public ResponseEntity<Void> deleteById(String guestId) {
        PartyModel party = partyDaoService.findOneBy(PartySpecification.withIdEqual(guestId));
        Page<DocumentModel> documentModels = documentDaoService.findAllBy(DocumentSpecification.withPartyId(guestId), Pageable.unpaged());
        for (DocumentModel documentModel : documentModels.getContent()) {
            identityDocumentService.deleteById(documentModel.getId());
        }
        partyDaoService.delete(party);
        return ResponseEntity.ok().build();
    }
}