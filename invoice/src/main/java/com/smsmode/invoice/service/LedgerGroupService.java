package com.smsmode.invoice.service;

import com.smsmode.invoice.resource.ledgergroup.LedgerGroupGetResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPatchResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LedgerGroupService {

    ResponseEntity<LedgerGroupGetResource> create(LedgerGroupPostResource ledgerGroupPostResource);

    ResponseEntity<LedgerGroupGetResource> retrieveById(String ledgerGroupId);

    ResponseEntity<Page<LedgerGroupGetResource>> retrieveAllByPage(String search, Boolean withParent, Boolean enabled,
                                                                   Boolean expanded, String parentId, Pageable pageable);

    ResponseEntity<LedgerGroupGetResource> updateById(String ledgerGroupId,
                                                      LedgerGroupPatchResource ledgerGroupPatchResource);

    ResponseEntity<Void> removeById(String ledgerGroupId);
}
