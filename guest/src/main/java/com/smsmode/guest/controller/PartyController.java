package com.smsmode.guest.controller;

import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.resource.guest.PartyItemGetResource;
import com.smsmode.guest.resource.guest.PartyPatchResource;
import com.smsmode.guest.resource.guest.PartyPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller for Guest operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@RequestMapping("/parties")
public interface PartyController {

    /**
     * Creates a new guest with optional documents and images.
     * Handles all cases: guest alone, guest+idDocument, guest+idDocument+images
     */
    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<PartyItemGetResource> post(
            @RequestPart("payload") @Valid PartyPostResource partyPostResource,
            @RequestPart(value = "file", required = false) MultipartFile identityDocument);

    /**
     * Retrieves all guests with optional search and pagination.
     */

    @GetMapping
    ResponseEntity<Page<PartyItemGetResource>> getAllByPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "type", required = false) PartyTypeEnum type,
            Pageable pageable);


    /* Retrieves a guest by ID.
     */
    @GetMapping("/{guestId}")
    ResponseEntity<PartyItemGetResource> getById(@PathVariable("guestId") String guestId);

    /**
     * Updates a guest partially.
     */

    @PatchMapping("/{partyId}")
    ResponseEntity<PartyItemGetResource> patchById(
            @PathVariable("partyId") String partyId,
            @RequestBody @Valid PartyPatchResource partyPatchResource);

    /**
     * Deletes a guest.
     */
    @DeleteMapping("/{guestId}")
    ResponseEntity<Void> deleteById(@PathVariable("guestId") String guestId);

}