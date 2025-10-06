package com.smsmode.uaa.service;

import com.smsmode.uaa.resource.account.AccountItemGetResource;
import com.smsmode.uaa.resource.account.AccountPatchResource;
import com.smsmode.uaa.resource.account.AccountPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<Page<AccountItemGetResource>> retrieveAccountsByPage(String search, Pageable pageable);

    ResponseEntity<AccountItemGetResource> retrieveAccountById(Long accountId);

    ResponseEntity<AccountItemGetResource> createAccount(AccountPostResource accountPostResource);

    ResponseEntity<AccountItemGetResource> updateAccountById(Long accountId, AccountPatchResource accountPatchResource);

}
