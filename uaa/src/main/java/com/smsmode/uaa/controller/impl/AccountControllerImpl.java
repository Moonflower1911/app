package com.smsmode.uaa.controller.impl;

import com.smsmode.uaa.controller.AccountController;
import com.smsmode.uaa.resource.account.AccountItemGetResource;
import com.smsmode.uaa.resource.account.AccountPostResource;
import com.smsmode.uaa.resource.account.AccountPatchResource;
import com.smsmode.uaa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public ResponseEntity<Page<AccountItemGetResource>> getAllByPage(String search, Pageable pageable) {
        return accountService.retrieveAccountsByPage(search, pageable);
    }

    @Override
    public ResponseEntity<AccountItemGetResource> getById(Long accountId) {
        return accountService.retrieveAccountById(accountId);
    }

    @Override
    public ResponseEntity<AccountItemGetResource> postAccount(AccountPostResource accountPostResource) {
        return accountService.createAccount(accountPostResource);
    }

    @Override
    public ResponseEntity<AccountItemGetResource> patchAccountById(Long accountId,
                                                                   AccountPatchResource accountPatchResource) {
        return accountService.updateAccountById(accountId, accountPatchResource);
    }
}
