package com.smsmode.invoice.controller.impl;

import com.smsmode.invoice.controller.AccountClassController;
import com.smsmode.invoice.resource.accountclass.AccountClassGetResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPatchResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPostResource;
import com.smsmode.invoice.service.AccountClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountClassControllerImpl implements AccountClassController {

    private final AccountClassService accountClassService;

    @Override
    public ResponseEntity<AccountClassGetResource> post(AccountClassPostResource accountClassPostResource) {
        return accountClassService.create(accountClassPostResource);
    }

    @Override
    public ResponseEntity<AccountClassGetResource> getById(String accountClassId) {
        return accountClassService.retrieveById(accountClassId);
    }

    @Override
    public ResponseEntity<Page<AccountClassGetResource>> getAllByPage(String search, Boolean enabled, Pageable pageable) {
        return accountClassService.retrieveAllByPage(search, enabled, pageable);
    }

    @Override
    public ResponseEntity<AccountClassGetResource> patchById(String accountClassId, AccountClassPatchResource accountClassPatchResource) {
        return accountClassService.updateById(accountClassId, accountClassPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String accountClassId) {
        return accountClassService.removeById(accountClassId);
    }
}
