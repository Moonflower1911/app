package com.smsmode.pricing.dao.old.repository;

import com.smsmode.pricing.model.old.OldRatePlanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RatePlan entity operations.
 */
@Repository
public interface OldRatePlanRepository extends JpaRepository<OldRatePlanModel, String>, JpaSpecificationExecutor<OldRatePlanModel> {
}