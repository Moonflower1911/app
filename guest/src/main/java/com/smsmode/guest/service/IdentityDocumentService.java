/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.service;

import com.smsmode.guest.model.DocumentModel;
import com.smsmode.guest.model.PartyModel;
import com.smsmode.guest.resource.iddocument.IdDocumentPatchResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentItemGetResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for IdentificationDocument business operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
public interface IdentityDocumentService {

    /**
     * Creates a new identification document for a guest.
     */
    ResponseEntity<IdentityDocumentItemGetResource> create(IdentityDocumentPostResource identityDocumentPostResource, MultipartFile identityDocumentFile);

    DocumentModel create(PartyModel partyModel, DocumentModel documentModel, MultipartFile identityDocumentFile);


    /**
     * Retrieves all identification documents for a guest with pagination and search support
     */
    ResponseEntity<Page<IdentityDocumentItemGetResource>> retrieveAllByGuestId(String guestId, String search, Pageable pageable);

    /**
     * Retrieves an identity document by identifier
     */
    ResponseEntity<IdentityDocumentItemGetResource> retrieveById(String identityDocumentId);

    /**
     * Updates an identification document partially.
     */

    ResponseEntity<IdentityDocumentItemGetResource> updateById(String idDocumentId, IdDocumentPatchResource idDocumentPatchResource, MultipartFile documentImage);

    /**
     * Deletes an identity document and associated media
     */
    ResponseEntity<Void> deleteById(String idDocumentId);

}