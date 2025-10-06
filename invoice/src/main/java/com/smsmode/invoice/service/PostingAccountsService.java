package com.smsmode.invoice.service;

import com.smsmode.invoice.resource.postingaccount.PostingAccountsGetResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPatchResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostingAccountsService {

    ResponseEntity<PostingAccountsGetResource> create(PostingAccountsPostResource postingAccountsPostResource);

    ResponseEntity<PostingAccountsGetResource> retrieveById(String postingAccountId);

    ResponseEntity<Page<PostingAccountsGetResource>> retrieveAllByPage(String search, String accountClass, Boolean enabled, Pageable pageable);

    ResponseEntity<PostingAccountsGetResource> updateById(String postingAccountId,
                                                          PostingAccountsPatchResource postingAccountsPatchResource);

    ResponseEntity<Void> removeById(String postingAccountId);
}
