package com.smsmode.guest.service;

import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.resource.guest.PartyItemGetResource;
import com.smsmode.guest.resource.guest.PartyPatchResource;
import com.smsmode.guest.resource.guest.PartyPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


/**
 * Service interface for Guest business operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
public interface PartyService {

    /**
     * Creates a new guest.
     */
    ResponseEntity<PartyItemGetResource> create(PartyPostResource partyPostResource, MultipartFile identityDocumentFile);

    /**
     * Retrieves all guests with pagination and optional search.
     */
    ResponseEntity<Page<PartyItemGetResource>> retrieveAllByPage(String search, PartyTypeEnum type, Pageable pageable);

    /**
     * Retrieves a guest by ID.
     */
    ResponseEntity<PartyItemGetResource> retrieveById(String guestId);

    /**
     * Updates a guest partially.
     */
    ResponseEntity<PartyItemGetResource> updateById(String partyId, PartyPatchResource partyPatchResource);

    /**
     * Deletes a guest by ID.
     */
    ResponseEntity<Void> deleteById(String guestId);
}