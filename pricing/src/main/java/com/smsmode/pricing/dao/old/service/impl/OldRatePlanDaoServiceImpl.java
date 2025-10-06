package com.smsmode.pricing.dao.old.service.impl;

import com.smsmode.pricing.dao.old.repository.OldRatePlanRepository;
import com.smsmode.pricing.dao.old.service.OldRatePlanDaoService;
import com.smsmode.pricing.dao.old.specification.OldRatePlanSpecification;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.old.OldRatePlanModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Implementation of RatePlanDaoService for managing rate plan data access.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OldRatePlanDaoServiceImpl implements OldRatePlanDaoService {

    private final OldRatePlanRepository oldRatePlanRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OldRatePlanModel save(OldRatePlanModel oldRatePlanModel) {
        log.debug("Saving rate plan: {}", oldRatePlanModel.getName());
        return oldRatePlanRepository.save(oldRatePlanModel);
    }

    @Override
    public List<OldRatePlanModel> findEnabledRatePlansWithOverlappingSegments(Set<String> segmentUuids) {
        if (CollectionUtils.isEmpty(segmentUuids)) {
            return Collections.emptyList();
        }

        Specification<OldRatePlanModel> spec = OldRatePlanSpecification.withOverlappingSegments(segmentUuids);
        return oldRatePlanRepository.findAll(spec);
    }


    @Override
    public void disableRatePlans(List<OldRatePlanModel> ratePlansToDisable) {
        log.debug("Disabling {} rate plans", ratePlansToDisable.size());
        for (OldRatePlanModel ratePlan : ratePlansToDisable) {
            ratePlan.setEnabled(false);
            oldRatePlanRepository.save(ratePlan);
        }
    }

    @Override
    public Page<OldRatePlanModel> findByUnitId(String unitId, String search, String segmentName, Pageable pageable) {
        log.debug("Finding rate plans for unit: {} with filters - search: {}, segmentName: {}",
                unitId, search, segmentName);

        // Build specification with all filters
        Specification<OldRatePlanModel> specification = Specification
                .where(OldRatePlanSpecification.withUnitUuid(unitId))
                .and(OldRatePlanSpecification.withNameContaining(search))
                .and(OldRatePlanSpecification.withSegmentNamesContaining(segmentName));
        return oldRatePlanRepository.findAll(specification, pageable);
    }

    @Override
    public OldRatePlanModel findById(String ratePlanId) {
        log.debug("Finding rate plan by ID: {}", ratePlanId);
        return oldRatePlanRepository.findById(ratePlanId).orElseThrow(() -> {
            log.debug("Rate plan with ID [{}] not found", ratePlanId);
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RATE_PLAN_NOT_FOUND,
                    "Rate plan with ID [" + ratePlanId + "] not found");
        });
    }

    @Override
    public void delete(OldRatePlanModel oldRatePlanModel) {
        log.debug("Deleting rate plan: {}", oldRatePlanModel.getId());
        oldRatePlanRepository.delete(oldRatePlanModel);
    }

    @Override
    public Page<OldRatePlanModel> findAll(Specification<OldRatePlanModel> specification, Pageable unpaged) {
        return oldRatePlanRepository.findAll(specification, unpaged);
    }

    @Override
    public boolean existsBy(Specification<OldRatePlanModel> specification) {
        return oldRatePlanRepository.exists(specification);
    }

    @Override
    public OldRatePlanModel findOneBy(Specification<OldRatePlanModel> specification) {
        return oldRatePlanRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No rate plan found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.RATE_PLAN_NOT_FOUND,
                    "No rate plan found based on your criteria");
        });
    }
}