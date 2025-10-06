package com.smsmode.pricing.controller.old;

import com.smsmode.pricing.resource.old.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller interface for RatePlan REST endpoints.
 */
@RequestMapping("old-rate-plans")
public interface OldRatePlanController {

    /**
     * Creates a new rate plan.
     */
    @PostMapping
    ResponseEntity<RatePlanGetResource> create(@Valid @RequestBody RatePlanPostResource ratePlanPostResource);

    /**
     * Retrieves all rate plans with pagination.
     */
    @GetMapping
    ResponseEntity<Page<RatePlanGetResource>> getAll(@RequestParam String unitId, @RequestParam(value = "search", required = false) String search,
                                                     @RequestParam(value = "segmentName", required = false) String segmentName, Pageable pageable);

    /**
     * Retrieves a rate plan by its ID.
     */
    @GetMapping("/{ratePlanId}")
    ResponseEntity<RatePlanGetResource> getById(@PathVariable String ratePlanId);

    /**
     * Updates an existing rate plan.
     */
    @PatchMapping("/{ratePlanId}")
    ResponseEntity<RatePlanGetResource> update(@PathVariable String ratePlanId,
                                               @Valid @RequestBody RatePlanPatchResource ratePlanPatchResource);

    /**
     * Deletes a rate plan by its ID.
     */
    @DeleteMapping("/{ratePlanId}")
    ResponseEntity<Void> delete(@PathVariable String ratePlanId);
}