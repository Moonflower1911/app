/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyGetResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPatchResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
@RequestMapping("cancellation-policies")
public interface CancellationPolicyController {

    @GetMapping
    ResponseEntity<Page<CancellationPolicyGetResource>> getAll(@RequestParam(required = false) String search, @RequestParam(required = false) Boolean enabled, Pageable pageable);

    @GetMapping("{cancellationPolicyId}")
    ResponseEntity<CancellationPolicyGetResource> getById(@PathVariable String cancellationPolicyId);

    @PostMapping
    ResponseEntity<CancellationPolicyGetResource> post(@Valid @RequestBody CancellationPolicyPostResource cancellationPolicyPostResource);

    @PatchMapping("{cancellationPolicyId}")
    ResponseEntity<CancellationPolicyGetResource> patchById(@PathVariable String cancellationPolicyId,
                                                            @RequestBody CancellationPolicyPatchResource cancellationPolicyPatchResource);

    @DeleteMapping("{cancellationPolicyId}")
    ResponseEntity<Void> deleteById(@PathVariable String cancellationPolicyId);
}
