package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.AccountClassDaoService;
import com.smsmode.invoice.dao.specification.AccountClassSpecification;
import com.smsmode.invoice.mapper.AccountClassMapper;
import com.smsmode.invoice.model.AccountClassModel;
import com.smsmode.invoice.resource.accountclass.AccountClassGetResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPatchResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPostResource;
import com.smsmode.invoice.service.AccountClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountClassServiceImpl implements AccountClassService {

    private final AccountClassMapper accountClassMapper;
    private final AccountClassDaoService accountClassDaoService;

    @Override
    @Transactional
    public ResponseEntity<AccountClassGetResource> create(AccountClassPostResource accountClassPostResource) {
        log.debug("Creating new AccountClass with name {}", accountClassPostResource.getName());

        AccountClassModel accountClassModel = accountClassMapper.postResourceToModel(accountClassPostResource);
        accountClassModel = accountClassDaoService.save(accountClassModel);

        AccountClassGetResource getResource = accountClassMapper.modelToGetResource(accountClassModel);

        return ResponseEntity.created(URI.create("")).body(getResource);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AccountClassGetResource> retrieveById(String accountClassId) {
        AccountClassModel accountClassModel =
                accountClassDaoService.findOneBy(AccountClassSpecification.withIdEqual(accountClassId));
        return ResponseEntity.ok(accountClassMapper.modelToGetResource(accountClassModel));
    }

    @Override
    public ResponseEntity<Page<AccountClassGetResource>> retrieveAllByPage(String search,Boolean enabled, Pageable pageable) {
        Specification<AccountClassModel> specification =
                Specification.where(AccountClassSpecification.withNameLike(search))
                        .and(AccountClassSpecification.withEnabled(enabled));;

        Page<AccountClassModel> accountClasses = accountClassDaoService.findAllBy(specification, pageable);

        return ResponseEntity.ok(accountClasses.map(accountClassMapper::modelToGetResource));
    }

    @Override
    @Transactional
    public ResponseEntity<AccountClassGetResource> updateById(String accountClassId, AccountClassPatchResource patchResource) {
        AccountClassModel model =
                accountClassDaoService.findOneBy(AccountClassSpecification.withIdEqual(accountClassId));

        if (model.isSystemCritical()) {
            log.warn("Attempt to patch system-critical AccountClass {}", accountClassId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        model = accountClassMapper.patchResourceToModel(patchResource, model);
        model = accountClassDaoService.save(model);

        return ResponseEntity.ok(accountClassMapper.modelToGetResource(model));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeById(String accountClassId) {
        AccountClassModel model =
                accountClassDaoService.findOneBy(AccountClassSpecification.withIdEqual(accountClassId));

        if (model.isSystemCritical()) {
            log.warn("Attempt to delete system-critical AccountClass {}", accountClassId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        accountClassDaoService.delete(model);
        return ResponseEntity.noContent().build();
    }
}
