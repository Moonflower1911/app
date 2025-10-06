/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.controller.impl;

import com.smsmode.guest.controller.DocumentController;
import com.smsmode.guest.resource.iddocument.IdDocumentPatchResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentItemGetResource;
import com.smsmode.guest.resource.iddocument.IdentityDocumentPostResource;
import com.smsmode.guest.service.IdentityDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of IdentificationDocumentController.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final IdentityDocumentService identityDocumentService;

    @Override
    public ResponseEntity<Page<IdentityDocumentItemGetResource>> getAllByPage(String guestId, String search, Pageable pageable) {
        return identityDocumentService.retrieveAllByGuestId(guestId, search, pageable);
    }

    @Override
    public ResponseEntity<IdentityDocumentItemGetResource> post(IdentityDocumentPostResource identityDocumentPostResource, MultipartFile documentImages) {
        return identityDocumentService.create(identityDocumentPostResource, documentImages);
    }


    @Override
    public ResponseEntity<IdentityDocumentItemGetResource> getById(String identityDocumentId) {
        return identityDocumentService.retrieveById(identityDocumentId);
    }

    @Override
    public ResponseEntity<IdentityDocumentItemGetResource> patchById(String identityDocumentId, IdDocumentPatchResource idDocumentPatchResource, MultipartFile documentImage) {
        return identityDocumentService.updateById(identityDocumentId, idDocumentPatchResource, documentImage);
    }

    @Override
    public ResponseEntity<Void> deleteById(String identityDocumentId) {
        return identityDocumentService.deleteById(identityDocumentId);
    }
}