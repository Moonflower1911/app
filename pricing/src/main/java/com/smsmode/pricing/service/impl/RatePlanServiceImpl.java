/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.CancellationPolicyDaoService;
import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.dao.service.UnitRefDaoService;
import com.smsmode.pricing.dao.specification.CancellationPolicySpecification;
import com.smsmode.pricing.dao.specification.RatePlanSpecification;
import com.smsmode.pricing.dao.specification.UnitRefSpecification;
import com.smsmode.pricing.mapper.RatePlanMapper;
import com.smsmode.pricing.model.CancellationPolicyModel;
import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.model.RatePlanUnitModel;
import com.smsmode.pricing.model.UnitRefModel;
import com.smsmode.pricing.resource.common.ResourceCodeRefGetResource;
import com.smsmode.pricing.resource.common.ResourceRefGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanItemGetResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPatchResource;
import com.smsmode.pricing.resource.rateplan.RatePlanPostResource;
import com.smsmode.pricing.service.RatePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatePlanServiceImpl implements RatePlanService {
    private final UnitRefDaoService unitRefDaoService;
    private final RatePlanDaoService ratePlanDaoService;
    private final RatePlanMapper ratePlanMapper;
    private final CancellationPolicyDaoService cancellationPolicyDaoService;

    @Override
    @Transactional
    public ResponseEntity<RatePlanGetResource> create(RatePlanPostResource ratePlanPostResource) {
        RatePlanModel ratePlanModel = ratePlanMapper.postResourceToModel(ratePlanPostResource);
        // fetch or create the unit refs
        List<UnitRefModel> units = this.ensureUnitsExist(ratePlanPostResource.getUnits());

        // clear inclusions just in case
        ratePlanModel.getRatePlanUnits().clear();

        // wrap each unit in a RatePlanUnitModel
        for (UnitRefModel unit : units) {
            RatePlanUnitModel rpUnit = new RatePlanUnitModel();
            rpUnit.setRatePlan(ratePlanModel);
            rpUnit.setUnit(unit);
            rpUnit.setEnabled(true); // default true at creation
            ratePlanModel.getRatePlanUnits().add(rpUnit);
        }

        ratePlanModel = ratePlanDaoService.save(ratePlanModel);

        return ResponseEntity
                .created(URI.create(""))
                .body(ratePlanMapper.modelToGetResource(ratePlanModel));
    }

    @Override
    @Transactional
    public ResponseEntity<RatePlanGetResource> update(String ratePlanId, RatePlanPatchResource ratePlanPatchResource) {
        // 1. Fetch existing rate plan
        RatePlanModel ratePlanModel = ratePlanDaoService.findOneBy(
                RatePlanSpecification.withIdEqual(ratePlanId)
        );

        // 2. Apply patch mapping to base fields (name, description, etc.)
        ratePlanModel = ratePlanMapper.patchResourceToModel(ratePlanPatchResource, ratePlanModel);

        if (ratePlanPatchResource.getCancellationPolicyId() != null) {
            if (ObjectUtils.isEmpty(ratePlanPatchResource.getCancellationPolicyId())) {
                ratePlanModel.setCancellationPolicy(null);
            } else {
                CancellationPolicyModel cancellationPolicy = cancellationPolicyDaoService.findOneBy(CancellationPolicySpecification.withId(ratePlanPatchResource.getCancellationPolicyId()));
                ratePlanModel.setCancellationPolicy(cancellationPolicy);
            }
        }

        if (!ObjectUtils.isEmpty(ratePlanPatchResource.getRestrictions())) {
            ratePlanModel.setMinLos(ratePlanPatchResource.getRestrictions().getMinLos());
            ratePlanModel.setMaxLos(ratePlanPatchResource.getRestrictions().getMaxLos());
            ratePlanModel.setMinLead(ratePlanPatchResource.getRestrictions().getMinLead());
            ratePlanModel.setMaxLead(ratePlanPatchResource.getRestrictions().getMaxLead());
        }

        // 3. Handle unit updates if present
        if (!CollectionUtils.isEmpty(ratePlanPatchResource.getUnits())) {
            // ensure units exist (existing or create new ones)
            List<UnitRefModel> units = this.ensureUnitsExist(ratePlanPatchResource.getUnits());

            // clear old associations
            ratePlanModel.getRatePlanUnits().clear();

            // recreate associations with default enabled = true
            for (UnitRefModel unit : units) {
                RatePlanUnitModel rpUnit = new RatePlanUnitModel();
                rpUnit.setRatePlan(ratePlanModel);
                rpUnit.setUnit(unit);
                rpUnit.setEnabled(true); // could also come from patchResource if exposed
                ratePlanModel.getRatePlanUnits().add(rpUnit);
            }
        }

        // 4. Save updated entity
        ratePlanModel = ratePlanDaoService.save(ratePlanModel);

        // 5. Return updated resource
        return ResponseEntity.ok(ratePlanMapper.modelToGetResource(ratePlanModel));
    }

    @Override
    public ResponseEntity<Page<RatePlanItemGetResource>> retrieveAll(String search, Pageable pageable) {

        Specification<RatePlanModel> specification = Specification.where(RatePlanSpecification.withNameLike(search).or(RatePlanSpecification.withCodeLike(search)));

        Page<RatePlanModel> ratePlanModels = ratePlanDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(ratePlanModels.map(ratePlanMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<RatePlanGetResource> retrieveById(String ratePlanId) {
        RatePlanModel ratePlanModel = ratePlanDaoService.findOneBy(RatePlanSpecification.withIdEqual(ratePlanId));
        return ResponseEntity.ok(ratePlanMapper.modelToGetResource(ratePlanModel));
    }

    @Transactional
    public List<UnitRefModel> ensureUnitsExist(List<ResourceCodeRefGetResource> unitRefs) {

        if (unitRefs == null || unitRefs.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> unitIds = unitRefs.stream()
                .map(ResourceRefGetResource::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Fetch already existing units
        List<UnitRefModel> existingUnits = unitRefDaoService.findAllBy(UnitRefSpecification.withIdIn(unitIds), Pageable.unpaged()).getContent();
        Set<String> existingIds = existingUnits.stream()
                .map(UnitRefModel::getId)
                .collect(Collectors.toSet());

        // Create new ones if missing
        List<UnitRefModel> newUnits = unitRefs.stream()
                .filter(ref -> ref.getId() == null || !existingIds.contains(ref.getId()))
                .map(ref -> {
                    UnitRefModel u = new UnitRefModel();
                    u.setId(ref.getId());
                    u.setName(ref.getName());
                    u.setCode(ref.getCode());
                    return u;
                })
                .toList();

        if (!newUnits.isEmpty()) {
            newUnits = unitRefDaoService.saveAll(newUnits); // batch insert
        }

        // Combine existing + new
        List<UnitRefModel> allUnits = new ArrayList<>();
        allUnits.addAll(existingUnits);
        allUnits.addAll(newUnits);
        return allUnits;
    }


}
