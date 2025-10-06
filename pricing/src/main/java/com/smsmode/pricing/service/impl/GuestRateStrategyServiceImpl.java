/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.GuestRateStrategyDaoService;
import com.smsmode.pricing.dao.specification.GuestRateStrategySpecification;
import com.smsmode.pricing.mapper.GuestRateStrategyMapper;
import com.smsmode.pricing.model.GuestRateRuleModel;
import com.smsmode.pricing.model.GuestRateStrategyModel;
import com.smsmode.pricing.resource.guestratestrategy.*;
import com.smsmode.pricing.service.GuestRateStrategyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GuestRateStrategyServiceImpl implements GuestRateStrategyService {

    private final GuestRateStrategyDaoService guestRateStrategyDaoService;
    private final GuestRateStrategyMapper guestRateStrategyMapper;

    @Override
    public ResponseEntity<Page<GuestRateStrategyItemGetResource>> retrieveAll(String search, Boolean enabled, Pageable pageable) {
        Specification<GuestRateStrategyModel> specification = Specification.where(GuestRateStrategySpecification.withNameLike(search))
                .and(GuestRateStrategySpecification.withEnabled(enabled));
        Page<GuestRateStrategyModel> guestRateStrategyModels = guestRateStrategyDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(guestRateStrategyModels.map(guestRateStrategyMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<GuestRateStrategyGetResource> retrieveById(String guestRateStrategyId) {
        GuestRateStrategyModel guestRateStrategyModel = guestRateStrategyDaoService.findOneBy(GuestRateStrategySpecification.withIdEqual(guestRateStrategyId));
        return ResponseEntity.ok(guestRateStrategyMapper.modelToGetResource(guestRateStrategyModel));
    }

    @Override
    public ResponseEntity<GuestRateStrategyGetResource> create(GuestRateStrategyPostResource guestRateStrategyPostResource) {
        GuestRateStrategyModel guestRateStrategyModel = guestRateStrategyMapper.postResourceToModel(guestRateStrategyPostResource);
        for (GuestRateRuleModel rule : guestRateStrategyModel.getRules()) {
            rule.setStrategy(guestRateStrategyModel);
        }
        guestRateStrategyModel = guestRateStrategyDaoService.save(guestRateStrategyModel);
        GuestRateStrategyGetResource guestRateStrategyGetResource = guestRateStrategyMapper.modelToGetResource(guestRateStrategyModel);
        return ResponseEntity.created(URI.create("")).body(guestRateStrategyGetResource);
    }

    @Override
    public ResponseEntity<GuestRateStrategyGetResource> update(String guestRateStrategyId, GuestRateStrategyPatchResource guestRateStrategyPatchResource) {

        GuestRateStrategyModel guestRateStrategyModel = guestRateStrategyDaoService.findOneBy(GuestRateStrategySpecification.withIdEqual(guestRateStrategyId));

        guestRateStrategyModel = guestRateStrategyMapper.patchResourceToModel(guestRateStrategyPatchResource, guestRateStrategyModel);

        if (!CollectionUtils.isEmpty(guestRateStrategyModel.getRules())) {
            updateRules(guestRateStrategyModel, guestRateStrategyPatchResource.getRules());
        }

        guestRateStrategyModel = guestRateStrategyDaoService.save(guestRateStrategyModel);
        return ResponseEntity.ok(guestRateStrategyMapper.modelToGetResource(guestRateStrategyModel));
    }

    private void updateRules(GuestRateStrategyModel guestRateStrategyModel, @Valid List<@Valid GuestRateRulePostResource> rules) {
        log.debug("Updating rules for guest rate strategy with id: {}", guestRateStrategyModel.getId());

        List<GuestRateRuleModel> existingRules = guestRateStrategyModel.getRules();

        // Si null, on garde les existantes
        if (rules == null) {
            log.debug("No additional guest prices in PATCH request, keeping existing");
            return;
        }

        // Si liste vide, on supprime tout
        if (rules.isEmpty()) {
            log.debug("Empty prices list in request, removing all existing prices");
            existingRules.clear();
            return;
        }

        // Map des existantes par ID
        Map<String, GuestRateRuleModel> existingById = existingRules.stream()
                .filter(rule -> rule.getId() != null)
                .collect(Collectors.toMap(
                        GuestRateRuleModel::getId,
                        price -> price
                ));

        List<GuestRateRuleModel> updatedPrices = new ArrayList<>();

        for (GuestRateRulePostResource priceResource : rules) {
            if (StringUtils.hasText(priceResource.getId())) {
                // ID fourni = UPDATE
                GuestRateRuleModel existingPrice = existingById.get(priceResource.getId());
                if (existingPrice != null) {
                    log.debug("Updating existing price with ID: {}", existingPrice.getId());
                    guestRateStrategyMapper.updateGuestRateRuleFromResource(priceResource, existingPrice);
                    updatedPrices.add(existingPrice);
                } else {
                    log.warn("Price with ID {} not found, creating new one", priceResource.getId());
                    GuestRateRuleModel newPrice = guestRateStrategyMapper.rulePostResourceToModel(priceResource);
                    newPrice.setStrategy(guestRateStrategyModel);
                    updatedPrices.add(newPrice);
                }
            } else {
                // Pas d'ID = CREATE
                log.debug("Creating new price for guestType: {}", priceResource.getGuestType());
                GuestRateRuleModel newPrice = guestRateStrategyMapper.rulePostResourceToModel(priceResource);
                newPrice.setStrategy(guestRateStrategyModel);
                updatedPrices.add(newPrice);
            }
        }

        // Remplacer la collection
        existingRules.clear();
        existingRules.addAll(updatedPrices);

        log.debug("After update - {} prices remaining", existingRules.size());
    }
}
