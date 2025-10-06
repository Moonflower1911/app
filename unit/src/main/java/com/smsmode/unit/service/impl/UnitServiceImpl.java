/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.*;
import com.smsmode.unit.service.UnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitDaoService unitDaoService;
    private final UnitMapper unitMapper;

    @Override
    @Transactional
    public ResponseEntity<UnitGetResource> create(UnitPostResource unitPostResource) {
        log.debug("Map unit post resource to model ...");
        UnitModel unitModel = unitMapper.postResourceToModel(unitPostResource);
        log.info("Unit model after mapping is: {}", unitModel);
        log.debug("Saving unit model to database ...");
        unitModel = unitDaoService.save(unitModel);
        log.info("Unit saved to database: {}", unitModel);

        if (unitModel.getNature().equals(UnitNatureEnum.MULTI_UNIT)) {
            if (!CollectionUtils.isEmpty(unitPostResource.getSubUnits())) {
                log.debug("SubUnits passed to be created/attached to this multi-unit ...");
                for (SubUnitResource subUnitResource : unitPostResource.getSubUnits()) {
                    log.debug("Subunit to save/attach: {}", subUnitResource);
                    UnitModel subUnit;
                    if (!ObjectUtils.isEmpty(subUnitResource.getUnitId())) {
                        log.debug("Retrieve unit with Id: {} from database ...", subUnitResource.getUnitId());
                        subUnit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(subUnitResource.getUnitId()));
                        log.info("Attaching multi-unit to sub-unit: {} and set priority to 0 ...", subUnit);
                        subUnit.setParent(unitModel);
                        subUnit.setPriority(0);
                        log.debug("Saving sub-unit to database ...");
                    } else {
                        log.debug("Map sub-unit to model ...");
                        subUnit = new UnitModel(subUnitResource.getName(), unitModel.getAddress(), unitModel.getContact(), subUnitResource.getReadiness(), subUnitResource.getPriority());
                        subUnit.setParent(unitModel);
                        log.debug("Sub-unit model after mapping is: {}", subUnit);
                    }
                    subUnit = unitDaoService.save(subUnit);
                    log.info("Sub-unit saved to database: {}", subUnit);
                }
            } else if (!ObjectUtils.isEmpty(unitPostResource.getQuantity())) {
                log.debug("Quantity passed for bulk creation ...");
                String prefix;
                List<UnitModel> subUnits = new ArrayList<>();
                if (ObjectUtils.isEmpty(unitPostResource.getSubUnitPrefix())) {
                    log.debug("No subUnit prefix defined, will use Sub Unit as a fallback ...");
                    prefix = "Sub Unit ";
                } else {
                    prefix = unitPostResource.getSubUnitPrefix();
                }
                log.debug("Will loop over quantity to create as many subUnits ...");
                for (int i = 0; i < unitPostResource.getQuantity(); i++) {
                    UnitModel subUnit = new UnitModel(prefix.concat(String.format("%03d", i + 1)), unitModel.getAddress(), unitModel.getContact(), false, i + 1);
                    subUnit.setParent(unitModel);
                    subUnits.add(subUnit);
                }
                log.debug("Constructed {} subUnits, that will be saved to database ...", subUnits);
                unitDaoService.saveAll(subUnits);
                log.info("{} Sub-unit saved to database", subUnits.size());
            }
        }
        return ResponseEntity.created(URI.create("")).body(unitMapper.modelToGetResource(unitModel));
    }

    @Override
    public ResponseEntity<Page<UnitItemGetResource>> retrieveAllByPage(String search, UnitNatureEnum nature,
                                                                       Boolean withParent, Pageable pageable) {
        Specification<UnitModel> unitSpecification;
        log.debug("Constructing unit specification ...");
        if (ObjectUtils.isEmpty(nature) && ObjectUtils.isEmpty(withParent)) {
            log.debug("Nature & withParent query parameters are not set, apply search filter on name and subtitle. " +
                    "Will retrieve only orphan units and multi units ...");
            unitSpecification = Specification.where(UnitSpecification.withNameLike(search)
                    .or(UnitSpecification.withSubTitleLike(search))).and(Specification.where(
                    UnitSpecification.withNature(UnitNatureEnum.SINGLE).and(UnitSpecification.withParent(false))
            ).or(UnitSpecification.withNature(UnitNatureEnum.MULTI_UNIT)));
        } else {
            log.debug("Nature or withParent query parameter are set. Will apply parameters accordingly ...");
            unitSpecification = Specification.where(UnitSpecification.withNameLike(search)
                            .or(UnitSpecification.withSubTitleLike(search))).and(UnitSpecification.withNature(nature))
                    .and(UnitSpecification.withParent(withParent));
        }
        log.debug("Retrieving units from database ...");
        Page<UnitModel> unitModelPage = unitDaoService.findAllBy(unitSpecification, pageable);
        log.info("Retrieved {} units from database", unitModelPage.getTotalPages());
        log.debug("Will map units to items resources ...");
        Page<UnitItemGetResource> unitItemGetResourcePage = unitModelPage.map(unitMapper::modelToItemGetResource);
        log.info("Unit item resources after mapping : {}", unitItemGetResourcePage);
        for (UnitItemGetResource unitItemGetResource : unitItemGetResourcePage.getContent()) {
            if (unitItemGetResource.getNature().equals(UnitNatureEnum.MULTI_UNIT)) {
                log.debug("Unit item resource: {} is of type MULTI_UNIT, will retrieve its subUnits from database if any ...", unitItemGetResource);
                Specification<UnitModel> subUnitSpecification = Specification.where(
                        UnitSpecification.withNature(UnitNatureEnum.SINGLE)).and(
                        UnitSpecification.withParentUnitId(unitItemGetResource.getId())
                );
                Page<UnitModel> subUnits = unitDaoService.findAllBy(subUnitSpecification, Pageable.unpaged());
                if (subUnits.isEmpty()) {
                    log.info("No sub-units found from database");
                } else {
                    log.info("Retrieved sub-units from database: {}", subUnits);
                    log.debug("Will map sub-units to items resources and enrich multi-unit resources...");
                    unitItemGetResource.setSubUnits(subUnits.map(unitMapper::modelToItemGetResource).getContent());
                    log.info("Multi-unit item resource after adding sub-units is: {}", unitItemGetResource);
                }
            }
        }
        return ResponseEntity.ok(unitItemGetResourcePage);
    }


    @Override
    public ResponseEntity<UnitGetResource> retrieveById(String unitId) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        return ResponseEntity.ok(unitMapper.modelToGetResource(unit));
    }

    @Override
    public ResponseEntity<UnitItemGetResource> addSubUnitToMultiUnit(String parentUnitId, SubUnitListResource subUnitListResource) {

        UnitModel parentUnit = unitDaoService.findById(parentUnitId);
        if (parentUnit == null || parentUnit.getNature() != UnitNatureEnum.MULTI_UNIT) {
            throw new IllegalArgumentException("Parent unit must exist and be of nature MULTI_UNIT.");
        }

        for (SubUnitResource subUnit : subUnitListResource.getSubUnits()) {
            processSubUnit(parentUnit, subUnit);
        }

        return ResponseEntity.ok(unitMapper.modelToItemGetResource(parentUnit));
    }

    @Override
    public void detachSubUnit(String subUnitId) {
        UnitModel subUnit = unitDaoService.findById(subUnitId);
        if (subUnit == null) {
            throw new IllegalArgumentException("Subunit not found");
        }

        if (subUnit.getParent() == null) {
            log.warn("Subunit [{}] is already detached", subUnitId);
            return;
        }

        subUnit.setParent(null);
        unitDaoService.save(subUnit);
    }

    @Override
    public ResponseEntity<Page<UnitItemGetResource>> getSubUnitsOfMultiUnit(String parentUnitId, String search, Pageable pageable) {
        boolean isSearchEmpty = (search == null || search.isBlank());

        Specification<UnitModel> spec = UnitSpecification.withParentUnitId(parentUnitId);

        if (!isSearchEmpty) {
            Specification<UnitModel> searchSpec = Specification
                    .where(UnitSpecification.withNameLike(search))
                    .or(UnitSpecification.withSubTitleLike(search));

            spec = spec.and(searchSpec);
        }

        Page<UnitModel> subUnits = unitDaoService.findAllBy(spec, pageable);
        Page<UnitItemGetResource> resourcePage = subUnits.map(unitMapper::modelToItemGetResource);

        return ResponseEntity.ok(resourcePage);
    }

    private void processSubUnit(UnitModel parentUnit, SubUnitResource subUnit) {
        if (subUnit.getUnitId() == null && (subUnit.getName() == null || subUnit.getName().isBlank())) {
            log.warn("Skipping sub-unit: both unitId and name are null or blank.");
            return;
        }

        UnitModel childUnit;

        if (subUnit.getUnitId() != null) {
            childUnit = unitDaoService.findById(subUnit.getUnitId());
            if (childUnit == null) {
                log.warn("Skipping sub-unit attachment: unit [{}] not found.", subUnit.getUnitId());
                return;
            }
            log.debug("Attaching existing unit [{}] to parent [{}]", subUnit.getUnitId(), parentUnit.getId());
        } else {
            childUnit = new UnitModel();
            childUnit.setName(subUnit.getName());
            childUnit.setNature(UnitNatureEnum.SINGLE);
            childUnit.setReadiness(Boolean.TRUE.equals(subUnit.getReadiness()));
            childUnit.setAddress(parentUnit.getAddress());
            childUnit.setContact(parentUnit.getContact());
            log.debug("Creating new sub-unit: {}", subUnit.getName());
        }

        childUnit.setParent(parentUnit);
        childUnit.setPriority(subUnit.getPriority() != null ? subUnit.getPriority() : 1);
        unitDaoService.save(childUnit);
    }

}
