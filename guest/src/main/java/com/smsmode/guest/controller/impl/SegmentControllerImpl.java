/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.controller.impl;

import com.smsmode.guest.controller.SegmentController;
import com.smsmode.guest.resource.segment.SegmentGetResource;
import com.smsmode.guest.resource.segment.SegmentItemGetResource;
import com.smsmode.guest.resource.segment.SegmentPatchResource;
import com.smsmode.guest.resource.segment.SegmentPostResource;
import com.smsmode.guest.service.SegmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SegmentControllerImpl implements SegmentController {

    private final SegmentService segmentService;

    @Override
    public ResponseEntity<Page<SegmentItemGetResource>> getAll(String search, Boolean withParent, Boolean enabled, Pageable pageable) {
        return segmentService.retrieveAllByPage(search, withParent, enabled, pageable);
    }

    @Override
    public ResponseEntity<SegmentGetResource> getById(String segmentId) {
        return segmentService.retrieveById(segmentId);
    }

    @Override
    public ResponseEntity<SegmentGetResource> post(SegmentPostResource segmentPostResource) {
        return segmentService.create(segmentPostResource);
    }

    @Override
    public ResponseEntity<SegmentGetResource> patchById(String segmentId, SegmentPatchResource segmentPatchResource) {
        return segmentService.updateById(segmentId, segmentPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String segmentId) {
        return segmentService.removeById(segmentId);
    }
}
