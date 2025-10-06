package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.dao.service.AccountDaoService;
import com.smsmode.uaa.dao.service.RoleDaoService;
import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.AccountSpecification;
import com.smsmode.uaa.dao.specification.RoleSpecification;
import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.mappers.AccountMapper;
import com.smsmode.uaa.model.AccountModel;
import com.smsmode.uaa.model.RoleModel;
import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.resource.account.AccountItemGetResource;
import com.smsmode.uaa.resource.account.AccountPatchResource;
import com.smsmode.uaa.resource.account.AccountPostResource;
import com.smsmode.uaa.service.AccountService;
import com.smsmode.uaa.service.MailingService;
import com.smsmode.uaa.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDaoService accountDaoService;
    private final UserDaoService userDaoService;
    private final RoleDaoService roleDaoService;
    private final AccountMapper accountMapper;
    private final TokenService tokenService;
    private final MailingService mailingService;

    @Override
    public ResponseEntity<Page<AccountItemGetResource>> retrieveAccountsByPage(String search, Pageable pageable) {
        Specification<AccountModel> specification = null;
        if (!ObjectUtils.isEmpty(search)) {
            specification = AccountSpecification.withNameLike(search);
        }
        Page<AccountModel> accountModels = accountDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(accountModels.map(accountMapper::modelToGetResource));
    }

    @Override
    @Transactional
    public ResponseEntity<AccountItemGetResource> retrieveAccountById(Long accountId) {
        AccountModel accountModel = accountDaoService.findById(accountId);
        AccountItemGetResource accountItemGetResource = accountMapper.modelToGetResource(accountModel);
        return ResponseEntity.ok(accountItemGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<AccountItemGetResource> createAccount(AccountPostResource accountPostResource) {

        AccountModel account = accountMapper.postResourceToModel(accountPostResource);
        account = accountDaoService.save(account);

        UserModel adminUser = new UserModel();
        adminUser.setFullName(accountPostResource.getManager().getFullName());
        adminUser.setEmail(accountPostResource.getManager().getEmail());
        adminUser.setMobile(accountPostResource.getManager().getMobile());
        adminUser.setAccount(account);
        adminUser.setPassword(null);
        RoleModel adminRole = roleDaoService.findOneBy(RoleSpecification.withRole(RoleEnum.ADMINISTRATOR));
        adminUser.getRoles().add(adminRole);

        adminUser = userDaoService.save(adminUser);

        TokenModel tokenModel = tokenService.generateAccountValidationToken(adminUser);
        mailingService.sendActivationAccountEmail(adminUser, tokenModel);

        return ResponseEntity.ok(accountMapper.modelToGetResource(account));
    }

    @Override
    public ResponseEntity<AccountItemGetResource> updateAccountById(Long accountId,
                                                                    AccountPatchResource accountPatchResource) {
        AccountModel existingAccount = accountDaoService.findById(accountId);
        existingAccount = accountMapper.patchResourceToModel(accountPatchResource, existingAccount);
        existingAccount = accountDaoService.save(existingAccount);
        return ResponseEntity.ok(accountMapper.modelToGetResource(existingAccount));
    }
}
