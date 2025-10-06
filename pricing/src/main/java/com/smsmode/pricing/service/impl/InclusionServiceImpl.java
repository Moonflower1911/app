/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.ChargeDaoService;
import com.smsmode.pricing.dao.service.RatePlanChargeDaoService;
import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.dao.specification.ChargeSpecification;
import com.smsmode.pricing.dao.specification.RatePlanChargeSpecification;
import com.smsmode.pricing.dao.specification.RatePlanSpecification;
import com.smsmode.pricing.mapper.RatePlanChargeMapper;
import com.smsmode.pricing.model.ChargeModel;
import com.smsmode.pricing.model.RatePlanChargeModel;
import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.resource.inclusion.InclusionItemGetResource;
import com.smsmode.pricing.resource.inclusion.InclusionPatchResource;
import com.smsmode.pricing.resource.inclusion.InclusionPostResource;
import com.smsmode.pricing.service.InclusionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.net.URI;


/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InclusionServiceImpl implements InclusionService {

    private final RatePlanDaoService ratePlanDaoService;
    private final ChargeDaoService chargeDaoService;
    private final RatePlanChargeDaoService ratePlanChargeDaoService;
    private final RatePlanChargeMapper ratePlanChargeMapper;

    @Override
    public ResponseEntity<Page<InclusionItemGetResource>> retrieveAll(String search, String ratePlanId, Boolean enabled, Pageable pageable) {
        Specification<RatePlanChargeModel> specification = Specification.where(
                        RatePlanChargeSpecification.withChargeNameLike(search)
                                .or(RatePlanChargeSpecification.withRatePlanNameLike(search)))
                .and(RatePlanChargeSpecification.withEnabledEqual(enabled))
                .and(RatePlanChargeSpecification.withRatePlanIdEqual(ratePlanId));
        Page<RatePlanChargeModel> ratePlanChargeModelPage = ratePlanChargeDaoService.findAll(specification, pageable);
        return ResponseEntity.ok(ratePlanChargeModelPage.map(ratePlanChargeMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> retrieveById(String inclusionId) {
        RatePlanChargeModel ratePlanChargeModel = ratePlanChargeDaoService.findOneBy(
                RatePlanChargeSpecification.withIdEqual(inclusionId));
        return ResponseEntity.ok(ratePlanChargeMapper.modelToItemGetResource(ratePlanChargeModel));
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> create(InclusionPostResource inclusionPostResource) {
        RatePlanChargeModel ratePlanChargeModel = ratePlanChargeMapper.postResourceToModel(inclusionPostResource);
        ChargeModel chargeModel = chargeDaoService.findOneBy(ChargeSpecification.withIdEqual(inclusionPostResource.getChargeId()));
        RatePlanModel ratePlanModel = ratePlanDaoService.findOneBy(RatePlanSpecification.withIdEqual(inclusionPostResource.getRatePlanId()));
        ratePlanChargeModel.setCharge(chargeModel);
        ratePlanChargeModel.setRatePlan(ratePlanModel);
        ratePlanChargeModel = ratePlanChargeDaoService.save(ratePlanChargeModel);
        return ResponseEntity.created(URI.create("")).body(ratePlanChargeMapper.modelToItemGetResource(ratePlanChargeModel));
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> updateById(String inclusionId, InclusionPatchResource inclusionPatchResource) {
        RatePlanChargeModel ratePlanChargeModel = ratePlanChargeDaoService.findOneBy(RatePlanChargeSpecification.withIdEqual(inclusionId));
        ratePlanChargeModel = ratePlanChargeMapper.patchResourceToModel(inclusionPatchResource, ratePlanChargeModel);
        ratePlanChargeModel = ratePlanChargeDaoService.save(ratePlanChargeModel);
        return ResponseEntity.ok(ratePlanChargeMapper.modelToItemGetResource(ratePlanChargeModel));
    }

    @Override
    public ResponseEntity<InclusionItemGetResource> removeById(String inclusionId) {
        ratePlanChargeDaoService.removeBy(RatePlanChargeSpecification.withIdEqual(inclusionId));
        return ResponseEntity.noContent().build();
    }
}
