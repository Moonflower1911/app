/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.VatRuleDaoService;
import com.smsmode.invoice.dao.specification.VatRuleSpecification;
import com.smsmode.invoice.mapper.VatRuleMapper;
import com.smsmode.invoice.model.VatRuleModel;
import com.smsmode.invoice.resource.vatrule.VatRuleGetResource;
import com.smsmode.invoice.resource.vatrule.VatRulePatchResource;
import com.smsmode.invoice.resource.vatrule.VatRulePostResource;
import com.smsmode.invoice.service.VatRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.web.PathMapper;
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
 * <p>Created 09 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VatRuleServiceImpl implements VatRuleService {

    private final VatRuleDaoService vatRuleDaoService;
    private final VatRuleMapper vatRuleMapper;
    private final PathMapper pathMapper;

    @Override
    public ResponseEntity<Page<VatRuleGetResource>> retrieveAll(String search, Pageable pageable) {
        Specification<VatRuleModel> specification = VatRuleSpecification.withNameLike(search);
        Page<VatRuleModel> vatRuleModels = vatRuleDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(vatRuleModels.map(vatRuleMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<VatRuleGetResource> create(VatRulePostResource vatRulePostResource) {
        VatRuleModel vatRuleModel = vatRuleMapper.postResourceToModel(vatRulePostResource);
        vatRuleModel = vatRuleDaoService.save(vatRuleModel);
        return ResponseEntity.created(URI.create("")).body(vatRuleMapper.modelToGetResource(vatRuleModel));
    }

    @Override
    public ResponseEntity<VatRuleGetResource> updateById(String vatRuleId, VatRulePatchResource vatRulePatchResource) {
        VatRuleModel vatRuleModel = vatRuleDaoService.findOneBy(VatRuleSpecification.withIdEqual(vatRuleId));
        vatRuleModel = vatRuleMapper.patchResourceToModel(vatRulePatchResource, vatRuleModel);
        vatRuleModel = vatRuleDaoService.save(vatRuleModel);
        return ResponseEntity.ok(vatRuleMapper.modelToGetResource(vatRuleModel));
    }

    @Override
    public ResponseEntity<Void> removeById(String vatRuleId) {
        vatRuleDaoService.deleteBy(VatRuleSpecification.withIdEqual(vatRuleId));
        return ResponseEntity.noContent().build();
    }

}
