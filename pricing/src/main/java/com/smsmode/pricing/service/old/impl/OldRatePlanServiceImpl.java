package com.smsmode.pricing.service.old.impl;

import com.smsmode.pricing.dao.old.service.OldRatePlanDaoService;
import com.smsmode.pricing.dao.old.specification.OldRatePlanSpecification;
import com.smsmode.pricing.embeddable.SegmentRefEmbeddable;
import com.smsmode.pricing.exception.ConflictException;
import com.smsmode.pricing.exception.enumeration.ConflictExceptionTitleEnum;
import com.smsmode.pricing.mapper.old.OldRatePlanMapper;
import com.smsmode.pricing.model.old.OldRatePlanModel;
import com.smsmode.pricing.resource.old.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.old.rateplan.RatePlanPostResource;
import com.smsmode.pricing.service.old.OldRatePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of RatePlanService for managing rate plan business operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OldRatePlanServiceImpl implements OldRatePlanService {

    private final OldRatePlanMapper oldRatePlanMapper;
    private final OldRatePlanDaoService oldRatePlanDaoService;

    @Override
    public ResponseEntity<RatePlanGetResource> create(RatePlanPostResource ratePlanPostResource) {

        if (ratePlanPostResource.getStandard().equals(Boolean.TRUE) && oldRatePlanDaoService.existsBy(OldRatePlanSpecification.withStandard(true))) {
            log.debug("A standard rate plan already exists, therefore we will set default to false ...");
            ratePlanPostResource.setStandard(false);
        }
        log.debug("Mapping rate post resource to model ...");
        OldRatePlanModel oldRatePlanModel = oldRatePlanMapper.postResourceToModel(ratePlanPostResource);
        log.info("Rate plan model is: {}", oldRatePlanModel);

        // If new rate plan is enabled, validate segment uniqueness
        if (Boolean.TRUE.equals(oldRatePlanModel.getEnabled())) {
            validateSegmentUniqueness(oldRatePlanModel, null);
        }

        // Save to database
        oldRatePlanModel = oldRatePlanDaoService.save(oldRatePlanModel);
        log.info("Successfully created rate plan with ID: {}", oldRatePlanModel.getId());

        // Transform model to GET resource
        RatePlanGetResource ratePlanGetResource = oldRatePlanMapper.modelToGetResource(oldRatePlanModel);

        return ResponseEntity.created(URI.create("")).body(ratePlanGetResource);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<RatePlanGetResource>> getAll(String unitId, String search, String segmentName, Pageable pageable) {
        log.debug("Retrieving all rate plans with pagination");

        // Get paginated data from database
        Page<OldRatePlanModel> ratePlanModelPage = oldRatePlanDaoService.findByUnitId(unitId, search, segmentName, pageable);
        log.info("Retrieved {} rate plans from database", ratePlanModelPage.getTotalElements());

        // Transform models to GET resources
        Page<RatePlanGetResource> response = ratePlanModelPage.map(oldRatePlanMapper::modelToGetResource);

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<RatePlanGetResource> getById(String ratePlanId) {
        log.debug("Retrieving rate plan by ID: {}", ratePlanId);

        // Find by ID (throws exception if not found)
        OldRatePlanModel oldRatePlanModel = oldRatePlanDaoService.findById(ratePlanId);

        // Transform model to GET resource
        RatePlanGetResource response = oldRatePlanMapper.modelToGetResource(oldRatePlanModel);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RatePlanGetResource> update(String ratePlanId, RatePlanPatchResource ratePlanPatchResource) {
        log.debug("Updating rate plan with ID: {}", ratePlanId);

        // Find existing rate plan
        OldRatePlanModel existingRatePlan = oldRatePlanDaoService.findById(ratePlanId);
        log.debug("Found existing rate plan: {}", existingRatePlan.getId());

        // Update model with new data
        oldRatePlanMapper.updateModelFromPatchResource(ratePlanPatchResource, existingRatePlan);

        // If updated rate plan is enabled, validate segment uniqueness
        if (Boolean.TRUE.equals(existingRatePlan.getEnabled())) {
            validateSegmentUniqueness(existingRatePlan, ratePlanId);
        }

        // Save updated model
        OldRatePlanModel updatedRatePlan = oldRatePlanDaoService.save(existingRatePlan);
        log.info("Successfully updated rate plan with ID: {}", ratePlanId);

        // Transform model to GET resource
        RatePlanGetResource response = oldRatePlanMapper.modelToGetResource(updatedRatePlan);

        return ResponseEntity.ok(response);
    }

    /**
     * Validates that segments are not already used by other enabled rate plans.
     */
    private void validateSegmentUniqueness(OldRatePlanModel oldRatePlanModel, String excludeRatePlanId) {
        Set<String> segmentUuids = oldRatePlanModel.getSegments().stream()
                .map(SegmentRefEmbeddable::getId)
                .collect(Collectors.toSet());

        List<OldRatePlanModel> overlappingPlans = oldRatePlanDaoService
                .findEnabledRatePlansWithOverlappingSegments(segmentUuids);

        // Exclure le rate plan actuel (pour les updates)
        if (excludeRatePlanId != null) {
            overlappingPlans.removeIf(plan -> plan.getId().equals(excludeRatePlanId));
        }

        if (!overlappingPlans.isEmpty()) {
            // Trouver quel segment est en conflit
            OldRatePlanModel conflictingPlan = overlappingPlans.get(0);
            String conflictingSegment = findConflictingSegmentName(oldRatePlanModel, conflictingPlan);

            throw new ConflictException(
                    ConflictExceptionTitleEnum.SEGMENT_ALREADY_EXISTS,
                    String.format("%s already exists in %s", conflictingSegment, conflictingPlan.getName())
            );
        }
    }

    private String findConflictingSegmentName(OldRatePlanModel newPlan, OldRatePlanModel existingPlan) {
        for (SegmentRefEmbeddable newSegment : newPlan.getSegments()) {
            for (SegmentRefEmbeddable existingSegment : existingPlan.getSegments()) {
                if (newSegment.getId().equals(existingSegment.getId())) {
                    return newSegment.getName();
                }
            }
        }
        return "Unknown segment";
    }


    @Override
    public ResponseEntity<Void> delete(String ratePlanId) {
        log.debug("Deleting rate plan with ID: {}", ratePlanId);

        // Find existing rate plan (throws exception if not found)
        OldRatePlanModel existingRatePlan = oldRatePlanDaoService.findById(ratePlanId);

        // Delete from database
        oldRatePlanDaoService.delete(existingRatePlan);
        log.info("Successfully deleted rate plan with ID: {}", ratePlanId);

        return ResponseEntity.noContent().build();
    }
}