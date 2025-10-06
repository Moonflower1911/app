/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.service.impl;

import com.smsmode.guest.dao.service.DocumentDaoService;
import com.smsmode.guest.dao.service.PartyDaoService;
import com.smsmode.guest.dao.specification.DocumentSpecification;
import com.smsmode.guest.dao.specification.PartySpecification;
import com.smsmode.guest.embeddable.MediaRefEmbeddable;
import com.smsmode.guest.exception.InternalServerException;
import com.smsmode.guest.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.guest.mapper.IdentityDocumentMapper;
import com.smsmode.guest.model.DocumentModel;
import com.smsmode.guest.model.PartyModel;
import com.smsmode.guest.resource.iddocument.IdDocumentPatchResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentItemGetResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentPostResource;
import com.smsmode.guest.resource.media.MediaGetResource;
import com.smsmode.guest.service.IdentityDocumentService;
import com.smsmode.guest.service.feign.MediaFeignService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityDocumentServiceImpl implements IdentityDocumentService {

    private final DocumentDaoService documentDaoService;
    private final IdentityDocumentMapper identityDocumentMapper;
    private final PartyDaoService partyDaoService;
    private final MediaFeignService mediaFeignService;
    @Value("${file.upload.identity-document}")
    public String identityDocumentPath;

    /**
     * Création avec support images multipart
     */
    @Override
    @Transactional
    public ResponseEntity<IdentityDocumentItemGetResource> create(IdentityDocumentPostResource idDocumentPostResource, MultipartFile identityDocumentFile) {
        PartyModel party = partyDaoService.findOneBy(PartySpecification.withIdEqual(idDocumentPostResource.getPartyId()));
        DocumentModel documentModel = identityDocumentMapper.postResourceToModel(idDocumentPostResource);
        documentModel = this.create(party, documentModel, identityDocumentFile);
        return ResponseEntity.created(URI.create("")).body(identityDocumentMapper.modelToItemGetResource(documentModel));
    }

    @Override
    @Transactional
    public DocumentModel create(PartyModel partyModel, DocumentModel documentModel, MultipartFile identityDocumentFile) {
        if (!ObjectUtils.isEmpty(documentModel)) {
            documentModel.setParty(partyModel);
            documentModel = documentDaoService.save(documentModel);
            if (!ObjectUtils.isEmpty(identityDocumentFile) && !identityDocumentFile.isEmpty()) {
                String filePath = identityDocumentPath.replace(":guestId", partyModel.getId());
                ResponseEntity<List<MediaGetResource>> mediaResponse = mediaFeignService.uploadMedia(filePath, Stream.of(identityDocumentFile).toArray(MultipartFile[]::new));
                List<MediaGetResource> mediaList = mediaResponse.getBody();
                if (mediaList == null || mediaList.isEmpty()) {
                    throw new InternalServerException(
                            InternalServerExceptionTitleEnum.FILE_UPLOAD,
                            "Media upload failed or returned no files.");
                }
                MediaRefEmbeddable media = new MediaRefEmbeddable();
                media.setId(mediaList.getFirst().getId());
                documentModel.setMedia(media);
                documentModel = documentDaoService.save(documentModel);
            }
            return documentModel;
        } else {
            log.debug("No document provided for party: {}", partyModel.getId());
            return null;
        }

    }

    @Override
    public ResponseEntity<Page<IdentityDocumentItemGetResource>> retrieveAllByGuestId(String guestId, String search, Pageable pageable) {
        Specification<DocumentModel> spec = DocumentSpecification.withPartyId(guestId).and(DocumentSpecification.withValueLike(search));
        Page<DocumentModel> idDocuments = documentDaoService.findAllBy(spec, pageable);
        return ResponseEntity.ok(idDocuments.map(identityDocumentMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<IdentityDocumentItemGetResource> retrieveById(String identityDocumentId) {
        Specification<DocumentModel> spec = Specification.where(
                DocumentSpecification.withId(identityDocumentId));
        DocumentModel documentModel = documentDaoService.findOneBy(spec);
        return ResponseEntity.ok(identityDocumentMapper.modelToItemGetResource(documentModel));
    }

    @Override
    @Transactional
    public ResponseEntity<IdentityDocumentItemGetResource> updateById(String documentId, IdDocumentPatchResource idDocumentPatchResource, MultipartFile documentImage) {
        log.debug("Updating document: {}", documentId);
        Specification<DocumentModel> spec = Specification.where(
                DocumentSpecification.withId(documentId));
        DocumentModel existingIdDocument = documentDaoService.findOneBy(spec);

        if (documentImage != null && !documentImage.isEmpty()) {
            if (existingIdDocument.getMedia() != null) {
                try {
                    mediaFeignService.deleteMediaById(existingIdDocument.getMedia().getId());
                    log.info("Old document image deleted for ID {}", existingIdDocument.getId());
                } catch (Exception e) {
                    log.warn("Failed to delete old media for document {}. Proceeding with update.", existingIdDocument.getId(), e);
                }
            }
        }

        DocumentModel updatedIdDocument = identityDocumentMapper.patchResourceToModel(idDocumentPatchResource, existingIdDocument);
        updatedIdDocument = this.create(updatedIdDocument.getParty(), updatedIdDocument, documentImage);

        return ResponseEntity.ok(identityDocumentMapper.modelToItemGetResource(updatedIdDocument));
    }

    /**
     * Suppression avec images associées
     */
    @Override
    @Transactional
    public ResponseEntity<Void> deleteById(String idDocumentId) {
        Specification<DocumentModel> spec = Specification.where(
                DocumentSpecification.withId(idDocumentId));
        DocumentModel documentModel = documentDaoService.findOneBy(spec);
        //call media service to delete media file
        try {
            mediaFeignService.deleteMediaById(documentModel.getMedia().getId());
        } catch (Exception e) {
            log.warn("Failed to delete media in Media service. Aborting deletion.");
            throw new InternalServerException(
                    InternalServerExceptionTitleEnum.FILE_UPLOAD,
                    "Unable to delete image from media storage.");
        }
        documentDaoService.delete(documentModel);
        return ResponseEntity.noContent().build();
    }
}