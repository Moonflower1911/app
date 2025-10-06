/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.inclusion.InclusionItemGetResource;
import com.smsmode.pricing.resource.inclusion.InclusionPatchResource;
import com.smsmode.pricing.resource.inclusion.InclusionPostResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;


/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
@RequestMapping("inclusions")
public interface InclusionController {

    @GetMapping
    ResponseEntity<Page<InclusionItemGetResource>> getAll(@RequestParam(name = "search", required = false) String search,
                                                    @RequestParam(name = "ratePlanId", required = false) String ratePlanId,
                                                    @RequestParam(name = "enabled", required = false) Boolean enabled,
                                                    Pageable pageable);

    @GetMapping("/{inclusionId}")
    ResponseEntity<InclusionItemGetResource> getById(@PathVariable("inclusionId") String inclusionId);

    @PostMapping
    ResponseEntity<InclusionItemGetResource> post(@RequestBody InclusionPostResource inclusionPostResource);

    @PatchMapping("/{inclusionId}")
    ResponseEntity<InclusionItemGetResource> patchById(@PathVariable String inclusionId, @RequestBody InclusionPatchResource inclusionPatchResource);

    @DeleteMapping("/{inclusionId}")
    ResponseEntity<InclusionItemGetResource> deleteById(@PathVariable String inclusionId);

}
