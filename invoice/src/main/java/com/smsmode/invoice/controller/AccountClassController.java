package com.smsmode.invoice.controller;

import com.smsmode.invoice.resource.accountclass.AccountClassGetResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPatchResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/account-classes")
public interface AccountClassController {

    @PostMapping
    ResponseEntity<AccountClassGetResource> post(@RequestBody @Valid AccountClassPostResource accountClassPostResource);

    @GetMapping("{accountClassId}")
    ResponseEntity<AccountClassGetResource> getById(@PathVariable("accountClassId") String accountClassId);

    @GetMapping
    ResponseEntity<Page<AccountClassGetResource>> getAllByPage(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean enabled,
            Pageable pageable
    );

    @PatchMapping("{accountClassId}")
    ResponseEntity<AccountClassGetResource> patchById(@PathVariable String accountClassId,
                                                      @Valid @RequestBody AccountClassPatchResource accountClassPatchResource);

    @DeleteMapping("{accountClassId}")
    ResponseEntity<Void> deleteById(@PathVariable String accountClassId);

}
