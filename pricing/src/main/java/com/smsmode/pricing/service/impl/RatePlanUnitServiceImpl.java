/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.RatePlanDaoService;
import com.smsmode.pricing.dao.service.RatePlanUnitDaoService;
import com.smsmode.pricing.dao.service.UnitRefDaoService;
import com.smsmode.pricing.dao.specification.RatePlanSpecification;
import com.smsmode.pricing.dao.specification.RatePlanUnitSpecification;
import com.smsmode.pricing.dao.specification.UnitRefSpecification;
import com.smsmode.pricing.mapper.RatePlanUnitMapper;
import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.model.RatePlanUnitModel;
import com.smsmode.pricing.model.UnitRefModel;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitGetResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPatchResource;
import com.smsmode.pricing.resource.rateplan.unit.RatePlanUnitPostResource;
import com.smsmode.pricing.service.RatePlanUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatePlanUnitServiceImpl implements RatePlanUnitService {

    private final RatePlanDaoService ratePlanDaoService;
    private final UnitRefDaoService unitRefDaoService;
    private final RatePlanUnitDaoService ratePlanUnitDaoService;
    private final RatePlanUnitMapper ratePlanUnitMapper;

    @Override
    public ResponseEntity<Page<RatePlanUnitGetResource>> retrieveAll(String ratePlanId, Pageable pageable) {
        Page<RatePlanUnitModel> ratePlanUnitModels = ratePlanUnitDaoService.findAll(RatePlanUnitSpecification.withRatePlanIdEqual(ratePlanId), pageable);
        return ResponseEntity.ok(ratePlanUnitModels.map(ratePlanUnitMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<RatePlanUnitGetResource> create(RatePlanUnitPostResource ratePlanUnitPostResource) {
        RatePlanModel ratePlanModel = ratePlanDaoService.findOneBy(RatePlanSpecification.withIdEqual(ratePlanUnitPostResource.getRatePlanId()));
        UnitRefModel unitRefModel;
        if (unitRefDaoService.existsBy(UnitRefSpecification.withIdEqual(ratePlanUnitPostResource.getUnit().getId()))) {
            unitRefModel = unitRefDaoService.findOneBy(UnitRefSpecification.withIdEqual(ratePlanUnitPostResource.getUnit().getId()));
        } else {
            unitRefModel = new UnitRefModel();
            unitRefModel.setId(ratePlanUnitPostResource.getUnit().getId());
            unitRefModel.setName(ratePlanUnitPostResource.getUnit().getName());
            unitRefModel.setCode(ratePlanUnitPostResource.getUnit().getCode());
            unitRefModel = unitRefDaoService.save(unitRefModel);
        }
        RatePlanUnitModel rpUnit = new RatePlanUnitModel();
        rpUnit.setRatePlan(ratePlanModel);
        rpUnit.setUnit(unitRefModel);
        rpUnit.setEnabled(ratePlanUnitPostResource.isEnabled());
        ratePlanUnitDaoService.save(rpUnit);
        return ResponseEntity.created(URI.create("")).body(ratePlanUnitMapper.modelToGetResource(rpUnit));
    }

    @Override
    public ResponseEntity<RatePlanUnitGetResource> updateById(String ratePlanUnitId, RatePlanUnitPatchResource ratePlanUnitPatchResource) {
        RatePlanUnitModel ratePlanUnitModel = ratePlanUnitDaoService.findOneBy(RatePlanUnitSpecification.withIdEqual(ratePlanUnitId));
        ratePlanUnitModel.setEnabled(ratePlanUnitPatchResource.getEnabled());
        ratePlanUnitModel = ratePlanUnitDaoService.save(ratePlanUnitModel);
        return ResponseEntity.ok(ratePlanUnitMapper.modelToGetResource(ratePlanUnitModel));
    }
}
