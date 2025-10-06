package com.smsmode.invoice.controller.impl;

import com.smsmode.invoice.controller.LedgerGroupController;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupGetResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPatchResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPostResource;
import com.smsmode.invoice.service.LedgerGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LedgerGroupControllerImpl implements LedgerGroupController {

    private final LedgerGroupService ledgerGroupService;

    @Override
    public ResponseEntity<LedgerGroupGetResource> post(LedgerGroupPostResource ledgerGroupPostResource) {
        return ledgerGroupService.create(ledgerGroupPostResource);
    }

    @Override
    public ResponseEntity<LedgerGroupGetResource> getById(String ledgerGroupId) {
        return ledgerGroupService.retrieveById(ledgerGroupId);
    }

    @Override
    public ResponseEntity<Page<LedgerGroupGetResource>> getAllByPage(
            String search,
            Boolean withParent,
            Boolean enabled,
            Boolean expanded,
            String parentId,
            Pageable pageable) {
        return ledgerGroupService.retrieveAllByPage(search, withParent, enabled, expanded, parentId, pageable);
    }

    @Override
    public ResponseEntity<LedgerGroupGetResource> patchById(String ledgerGroupId, LedgerGroupPatchResource ledgerGroupPatchResource) {
        return ledgerGroupService.updateById(ledgerGroupId, ledgerGroupPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String ledgerGroupId) {
        return ledgerGroupService.removeById(ledgerGroupId);
    }
}
