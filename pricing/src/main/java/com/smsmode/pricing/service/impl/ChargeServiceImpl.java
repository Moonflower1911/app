/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.ChargeDaoService;
import com.smsmode.pricing.dao.service.GuestRateStrategyDaoService;
import com.smsmode.pricing.dao.specification.ChargeSpecification;
import com.smsmode.pricing.enumeration.ChargeTypeEnum;
import com.smsmode.pricing.mapper.ChargeMapper;
import com.smsmode.pricing.model.ChargeModel;
import com.smsmode.pricing.resource.charge.ChargeItemGetResource;
import com.smsmode.pricing.resource.charge.ChargePatchResource;
import com.smsmode.pricing.resource.charge.ChargePostResource;
import com.smsmode.pricing.service.ChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

    private final ChargeDaoService chargeDaoService;
    private final GuestRateStrategyDaoService guestRateStrategyDaoService;
    private final ChargeMapper chargeMapper;

    @Override
    public ResponseEntity<Page<ChargeItemGetResource>> retrieveAll(String search, Boolean isExtra, Boolean isPackage, Boolean enabled, Pageable pageable) {
        Specification<ChargeModel> specification = Specification.where(ChargeSpecification.withNameLike(search)
                .or(ChargeSpecification.withCodeLike(search)))
                .and(ChargeSpecification.withIsExtra(isExtra))
                .and(ChargeSpecification.withIsPackage(isPackage))
                .and(ChargeSpecification.withEnabledEqual(enabled));
        Page<ChargeModel> chargeModelPage = chargeDaoService.findAll(specification, pageable);
        return ResponseEntity.ok(chargeModelPage.map(chargeMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<ChargeItemGetResource> retrieveById(String chargeId) {
        ChargeModel chargeModel = chargeDaoService.findOneBy(ChargeSpecification.withIdEqual(chargeId));
        return ResponseEntity.ok(chargeMapper.modelToItemGetResource(chargeModel));
    }

    @Override
    public ResponseEntity<ChargeItemGetResource> create(ChargePostResource chargePostResource) {
        ChargeModel chargeModel = chargeMapper.postResourceToModel(chargePostResource);
/*        if (!ObjectUtils.isEmpty(chargePostResource.getGuestRateStrategyId())) {
            GuestRateStrategyModel guestRateStrategyModel = guestRateStrategyDaoService
                    .findOneBy(GuestRateStrategySpecification.withIdEqual(chargePostResource.getGuestRateStrategyId()));
            if (!ObjectUtils.isEmpty(guestRateStrategyModel)) {
                chargeModel.setGuestRateStrategy(guestRateStrategyModel);
            }
        }*/
        chargeModel = chargeDaoService.save(chargeModel);
        return ResponseEntity.created(URI.create("")).body(chargeMapper.modelToItemGetResource(chargeModel));
    }

    @Override
    public ResponseEntity<ChargeItemGetResource> updateById(String chargeId, ChargePatchResource chargePatchResource) {
        ChargeModel chargeModel = chargeDaoService.findOneBy(ChargeSpecification.withIdEqual(chargeId));
        chargeModel = chargeMapper.patchResourceToModel(chargePatchResource, chargeModel);
/*        if (chargePatchResource.getGuestRateStrategyId() != null) {
            if (ObjectUtils.isEmpty(chargePatchResource.getGuestRateStrategyId())) {
                chargeModel.setGuestRateStrategy(null);
            } else {
                GuestRateStrategyModel guestRateStrategyModel = guestRateStrategyDaoService.findOneBy(
                        GuestRateStrategySpecification.withIdEqual(chargePatchResource.getGuestRateStrategyId()));
                if (!ObjectUtils.isEmpty(guestRateStrategyModel)) {
                    chargeModel.setGuestRateStrategy(guestRateStrategyModel);
                }
            }
        }*/
        chargeModel = chargeDaoService.save(chargeModel);
        return ResponseEntity.ok(chargeMapper.modelToItemGetResource(chargeModel));
    }

    @Override
    public ResponseEntity<Void> deleteById(String chargeId) {
        chargeDaoService.deleteBy(ChargeSpecification.withIdEqual(chargeId));
        return ResponseEntity.noContent().build();
    }
}
