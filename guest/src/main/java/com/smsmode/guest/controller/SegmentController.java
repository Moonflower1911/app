/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.controller;

import com.smsmode.guest.resource.segment.SegmentGetResource;
import com.smsmode.guest.resource.segment.SegmentItemGetResource;
import com.smsmode.guest.resource.segment.SegmentPatchResource;
import com.smsmode.guest.resource.segment.SegmentPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
@RequestMapping("/segments")
public interface SegmentController {

    @GetMapping
    ResponseEntity<Page<SegmentItemGetResource>> getAll(@RequestParam(value = "search", required = false) String search,
                                                        @RequestParam(value = "withParent", required = false) Boolean withParent,
                                                        @RequestParam(value = "enabled", required = false) Boolean enabled,
                                                        Pageable pageable);

    @GetMapping("/{segmentId}")
    ResponseEntity<SegmentGetResource> getById(@PathVariable("segmentId") String segmentId);

    @PostMapping
    ResponseEntity<SegmentGetResource> post(@Valid @RequestBody SegmentPostResource segmentPostResource);

    @PatchMapping("/{segmentId}")
    ResponseEntity<SegmentGetResource> patchById(@PathVariable("segmentId") String segmentId, @Valid @RequestBody SegmentPatchResource segmentPatchResource);

    @DeleteMapping("/{segmentId}")
    ResponseEntity<Void> deleteById(@PathVariable("segmentId") String segmentId);
}
