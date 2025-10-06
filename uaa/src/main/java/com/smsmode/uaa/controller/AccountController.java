package com.smsmode.uaa.controller;

import com.smsmode.uaa.resource.account.AccountItemGetResource;
import com.smsmode.uaa.resource.account.AccountPostResource;
import com.smsmode.uaa.resource.account.AccountPatchResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accounts")
public interface AccountController {

    @GetMapping
    ResponseEntity<Page<AccountItemGetResource>> getAllByPage(@RequestParam(required = false) String search,
                                                              Pageable pageable);

    @GetMapping("/{accountId}")
    ResponseEntity<AccountItemGetResource> getById(@PathVariable("accountId") Long accountId);

    @PostMapping
    ResponseEntity<AccountItemGetResource> postAccount(@RequestBody @Valid AccountPostResource accountPostResource);

    @PatchMapping("/{accountId}")
    ResponseEntity<AccountItemGetResource> patchAccountById(@PathVariable("accountId") Long accountId,
                                                            @RequestBody @Valid AccountPatchResource accountPatchResource);

}
