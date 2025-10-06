package com.smsmode.invoice.controller;

import com.smsmode.invoice.resource.postingaccount.PostingAccountsGetResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPatchResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/posting-accounts")
public interface PostingAccountsController {

    @PostMapping
    ResponseEntity<PostingAccountsGetResource> post(@RequestBody @Valid PostingAccountsPostResource postingAccountsPostResource);

    @GetMapping("{postingAccountId}")
    ResponseEntity<PostingAccountsGetResource> getById(@PathVariable("postingAccountId") String postingAccountId);

    @GetMapping
    ResponseEntity<Page<PostingAccountsGetResource>> getAllByPage(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String accountClass,
            @RequestParam(required = false) Boolean enabled,
            Pageable pageable
    );

    @PatchMapping("{postingAccountId}")
    ResponseEntity<PostingAccountsGetResource> patchById(@PathVariable String postingAccountId,
                                                         @Valid @RequestBody PostingAccountsPatchResource postingAccountsPatchResource);

    @DeleteMapping("{postingAccountId}")
    ResponseEntity<Void> deleteById(@PathVariable String postingAccountId);

}
