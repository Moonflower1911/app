package com.smsmode.guest.controller.impl;

import com.smsmode.guest.controller.PartyController;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.resource.guest.PartyItemGetResource;

import com.smsmode.guest.resource.guest.PartyPatchResource;
import com.smsmode.guest.resource.guest.PartyPostResource;
import com.smsmode.guest.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of GuestController.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class PartyControllerImpl implements PartyController {

    private final PartyService partyService;

    @Override
    public ResponseEntity<PartyItemGetResource> post(PartyPostResource partyPostResource, MultipartFile identityDocument) {
        return partyService.create(partyPostResource, identityDocument);
    }

    @Override
    public ResponseEntity<Page<PartyItemGetResource>> getAllByPage(String search, PartyTypeEnum type, Pageable pageable) {
        return partyService.retrieveAllByPage(search, type, pageable);
    }

    @Override
    public ResponseEntity<PartyItemGetResource> getById(String guestId) {
        return partyService.retrieveById(guestId);
    }

    @Override
    public ResponseEntity<PartyItemGetResource> patchById(String partyId, PartyPatchResource partyPatchResource) {
        return partyService.updateById(partyId, partyPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String guestId) {
        return partyService.deleteById(guestId);
    }
}