package com.smsmode.invoice.controller;

import com.smsmode.invoice.resource.ledgergroup.LedgerGroupGetResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPatchResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ledger-groups")
public interface LedgerGroupController {

    @PostMapping
    ResponseEntity<LedgerGroupGetResource> post(@RequestBody @Valid LedgerGroupPostResource ledgerGroupPostResource);

    @GetMapping("{ledgerGroupId}")
    ResponseEntity<LedgerGroupGetResource> getById(@PathVariable("ledgerGroupId") String ledgerGroupId);

    @GetMapping
    ResponseEntity<Page<LedgerGroupGetResource>> getAllByPage(
            @RequestParam(required = false) String search,
            @RequestParam(value = "withParent", required = false) Boolean withParent,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            @RequestParam(value = "expanded", required = false) Boolean expanded,
            @RequestParam(value = "parentId", required = false) String parentId,
            Pageable pageable
    );

    @PatchMapping("{ledgerGroupId}")
    ResponseEntity<LedgerGroupGetResource> patchById(@PathVariable String ledgerGroupId,
                                                     @Valid @RequestBody LedgerGroupPatchResource ledgerGroupPatchResource);

    @DeleteMapping("{ledgerGroupId}")
    ResponseEntity<Void> deleteById(@PathVariable String ledgerGroupId);

}
