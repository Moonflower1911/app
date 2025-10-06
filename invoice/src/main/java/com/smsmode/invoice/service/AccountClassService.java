package com.smsmode.invoice.service;

import com.smsmode.invoice.resource.accountclass.AccountClassGetResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPatchResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AccountClassService {

    ResponseEntity<AccountClassGetResource> create(AccountClassPostResource accountClassPostResource);

    ResponseEntity<AccountClassGetResource> retrieveById(String accountClassId);

    ResponseEntity<Page<AccountClassGetResource>> retrieveAllByPage(String search, Boolean enabled, Pageable pageable);

    ResponseEntity<AccountClassGetResource> updateById(String accountClassId, AccountClassPatchResource accountClassPatchResource);

    ResponseEntity<Void> removeById(String accountClassId);
}
