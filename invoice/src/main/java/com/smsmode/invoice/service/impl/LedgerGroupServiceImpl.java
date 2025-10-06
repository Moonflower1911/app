package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.LedgerGroupDaoService;
import com.smsmode.invoice.dao.specification.LedgerGroupSpecification;
import com.smsmode.invoice.mapper.LedgerGroupMapper;
import com.smsmode.invoice.model.LedgerGroupModel;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupGetResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPatchResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPostResource;
import com.smsmode.invoice.service.LedgerGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LedgerGroupServiceImpl implements LedgerGroupService {

    private final LedgerGroupMapper ledgerGroupMapper;
    private final LedgerGroupDaoService ledgerGroupDaoService;

    @Override
    @Transactional
    public ResponseEntity<LedgerGroupGetResource> create(LedgerGroupPostResource postResource) {
        log.debug("Creating new LedgerGroup with name {}", postResource.getName());

        LedgerGroupModel model = ledgerGroupMapper.postResourceToModel(postResource);

        if (!ObjectUtils.isEmpty(postResource.getParentId())) {
            LedgerGroupModel parent = ledgerGroupDaoService.findOneBy(
                    LedgerGroupSpecification.withIdEqual(postResource.getParentId()));
            model.setParent(parent);
        }

        model = ledgerGroupDaoService.save(model);
        LedgerGroupGetResource getResource = ledgerGroupMapper.modelToGetResource(model);

        return ResponseEntity.created(URI.create("")).body(getResource);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LedgerGroupGetResource> retrieveById(String ledgerGroupId) {
        LedgerGroupModel model =
                ledgerGroupDaoService.findOneBy(LedgerGroupSpecification.withIdEqual(ledgerGroupId));
        return ResponseEntity.ok(ledgerGroupMapper.modelToGetResource(model));
    }

    @Override
    public ResponseEntity<Page<LedgerGroupGetResource>> retrieveAllByPage(String search,
                                                                          Boolean withParent,
                                                                          Boolean enabled,
                                                                          Boolean expanded,
                                                                          String parentId,
                                                                          Pageable pageable) {
        Specification<LedgerGroupModel> specification = Specification
                .where(LedgerGroupSpecification.withNameLike(search))
                .and(LedgerGroupSpecification.withParent(withParent))
                .and(LedgerGroupSpecification.withEnabled(enabled))
                .and(LedgerGroupSpecification.withParentId(parentId));

        Page<LedgerGroupModel> ledgerGroups = ledgerGroupDaoService.findAllBy(specification, pageable);

        Page<LedgerGroupGetResource> resources = ledgerGroups.map(model -> {
            LedgerGroupGetResource resource = ledgerGroupMapper.modelToGetResource(model);

            if (ObjectUtils.isEmpty(model.getParent())) {
                long count = ledgerGroupDaoService.countByParentId(model.getId());
                resource.setSubledgerCount(count);

                if(Boolean.TRUE.equals(expanded)){
                    Page<LedgerGroupModel> subLedgers = ledgerGroupDaoService.findAllBy(Specification.where(
                            LedgerGroupSpecification.withParentId(resource.getId())
                    ), Pageable.unpaged());
                    resource.setSubLedgers(subLedgers.getContent().stream().map(ledgerGroupMapper::modelToGetResource).toList());
                }
            }

            if (parentId != null) {
                resource.setParent(null);
            }

            return resource;
        });

        return ResponseEntity.ok(resources);
    }


    @Override
    @Transactional
    public ResponseEntity<LedgerGroupGetResource> updateById(String ledgerGroupId, LedgerGroupPatchResource patchResource) {
        LedgerGroupModel model =
                ledgerGroupDaoService.findOneBy(LedgerGroupSpecification.withIdEqual(ledgerGroupId));

        model = ledgerGroupMapper.patchResourceToModel(patchResource, model);

        if (patchResource.getParentId() != null) {
            if (!ObjectUtils.isEmpty(patchResource.getParentId())) {
                LedgerGroupModel parent = ledgerGroupDaoService.findOneBy(
                        LedgerGroupSpecification.withIdEqual(patchResource.getParentId()));
                model.setParent(parent);
            } else {
                model.setParent(null);
            }
        }

        model = ledgerGroupDaoService.save(model);
        return ResponseEntity.ok(ledgerGroupMapper.modelToGetResource(model));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeById(String ledgerGroupId) {
        LedgerGroupModel model =
                ledgerGroupDaoService.findOneBy(LedgerGroupSpecification.withIdEqual(ledgerGroupId));
        ledgerGroupDaoService.delete(model);
        return ResponseEntity.noContent().build();
    }
}
