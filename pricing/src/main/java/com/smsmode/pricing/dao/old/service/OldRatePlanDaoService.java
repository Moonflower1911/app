package com.smsmode.pricing.dao.old.service;

import com.smsmode.pricing.model.old.OldRatePlanModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

/**
 * DAO Service interface for RatePlan data access operations.
 */
public interface OldRatePlanDaoService {

    /**
     * Saves a rate plan.
     */
    OldRatePlanModel save(OldRatePlanModel oldRatePlanModel);

    /**
     * Finds rate plans that have at least one segment in common with the given segments.
     */
    List<OldRatePlanModel> findEnabledRatePlansWithOverlappingSegments(Set<String> segmentUuids);

    /**
     * Disables multiple rate plans by setting enabled=false.
     */
    void disableRatePlans(List<OldRatePlanModel> ratePlansToDisable);

    /**
     * Finds all rate plans related to a unit with pagination.
     */
    Page<OldRatePlanModel> findByUnitId(String unitId, String search, String segmentName, Pageable pageable);

    /**
     * Finds a rate plan by its ID.
     */
    OldRatePlanModel findById(String ratePlanId);

    /**
     * Deletes a rate plan.
     */
    void delete(OldRatePlanModel oldRatePlanModel);

    Page<OldRatePlanModel> findAll(Specification<OldRatePlanModel> specification, Pageable unpaged);

    boolean existsBy(Specification<OldRatePlanModel> specification);

    OldRatePlanModel findOneBy(Specification<OldRatePlanModel> specification);
}