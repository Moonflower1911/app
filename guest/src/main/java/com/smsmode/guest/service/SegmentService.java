/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.service;

import com.smsmode.guest.resource.segment.SegmentGetResource;
import com.smsmode.guest.resource.segment.SegmentItemGetResource;
import com.smsmode.guest.resource.segment.SegmentPatchResource;
import com.smsmode.guest.resource.segment.SegmentPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
public interface SegmentService {
    ResponseEntity<Page<SegmentItemGetResource>> retrieveAllByPage(String search, Boolean withParent, Boolean enabled, Pageable pageable);

    ResponseEntity<SegmentGetResource> retrieveById(String segmentId);

    ResponseEntity<SegmentGetResource> create(SegmentPostResource segmentPostResource);

    ResponseEntity<SegmentGetResource> updateById(String segmentId, SegmentPatchResource segmentPatchResource);

    ResponseEntity<Void> removeById(String segmentId);
}
