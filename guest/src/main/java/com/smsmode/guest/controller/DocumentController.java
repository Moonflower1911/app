/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.controller;

import com.smsmode.guest.resource.iddocument.IdDocumentPatchResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentItemGetResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller for IdentificationDocument operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@RequestMapping("/documents")
public interface DocumentController {
    /**
     * Retrieves all identity documents for a guest with pagination and search.
     */
    @GetMapping
    ResponseEntity<Page<IdentityDocumentItemGetResource>> getAllByPage(
            @RequestParam("guestId") String guestId,
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable);

    /**
     * Retrieves an identification document by ID.
     */
    @GetMapping("/{identityDocumentId}")
    ResponseEntity<IdentityDocumentItemGetResource> getById(
            @PathVariable("identityDocumentId") String identityDocumentId);

    /**
     * Creates a new identity document with optional images (multipart support).
     */
    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<IdentityDocumentItemGetResource> post(
            @RequestPart("payload") @Valid IdentityDocumentPostResource identityDocumentPostResource,
            @RequestPart(value = "file", required = false) MultipartFile documentImages);

    /**
     * Updates an identity document partially (without images).
     */
    @PatchMapping(value = "/{identityDocumentId}", consumes = "multipart/form-data")
    ResponseEntity<IdentityDocumentItemGetResource> patchById(
            @PathVariable("identityDocumentId") String identityDocumentId,
            @RequestPart("payload") @Valid IdDocumentPatchResource idDocumentPatchResource,
            @RequestPart(value = "file", required = false) MultipartFile documentImage);

    /**
     * Deletes an identity document and its associated images.
     */
    @DeleteMapping("/{identityDocumentId}")
    ResponseEntity<Void> deleteById(
            @PathVariable("identityDocumentId") String identityDocumentId);
}
