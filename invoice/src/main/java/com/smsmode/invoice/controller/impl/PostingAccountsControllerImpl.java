package com.smsmode.invoice.controller.impl;

import com.smsmode.invoice.controller.PostingAccountsController;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsGetResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPatchResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPostResource;
import com.smsmode.invoice.service.PostingAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostingAccountsControllerImpl implements PostingAccountsController {

    private final PostingAccountsService postingAccountsService;

    @Override
    public ResponseEntity<PostingAccountsGetResource> post(PostingAccountsPostResource postingAccountsPostResource) {
        return postingAccountsService.create(postingAccountsPostResource);
    }

    @Override
    public ResponseEntity<PostingAccountsGetResource> getById(String postingAccountId) {
        return postingAccountsService.retrieveById(postingAccountId);
    }

    @Override
    public ResponseEntity<Page<PostingAccountsGetResource>> getAllByPage(String search, String accountClass, Boolean enabled, Pageable pageable) {
        return postingAccountsService.retrieveAllByPage(search, accountClass, enabled, pageable);
    }

    @Override
    public ResponseEntity<PostingAccountsGetResource> patchById(
            String postingAccountId,
            PostingAccountsPatchResource postingAccountsPatchResource
    ) {
        return postingAccountsService.updateById(postingAccountId, postingAccountsPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteById(String postingAccountId) {
        return postingAccountsService.removeById(postingAccountId);
    }
}
