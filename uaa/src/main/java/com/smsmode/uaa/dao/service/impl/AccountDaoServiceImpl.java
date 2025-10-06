package com.smsmode.uaa.dao.service.impl;

import com.smsmode.uaa.dao.repository.AccountRepository;
import com.smsmode.uaa.dao.service.AccountDaoService;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.uaa.model.AccountModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDaoServiceImpl implements AccountDaoService {

    private final AccountRepository accountRepository;

    @Override
    public AccountModel findOneBy(Specification<AccountModel> specification) {
        return accountRepository
                .findOne(specification)
                .orElseThrow(() -> {
                    log.debug("Couldn't find any account with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.ACCOUNT_NOT_FOUND,
                            "No account found with the specified criteria");
                });
    }

    @Override
    public AccountModel findById(Long accountId) {
        return accountRepository
                .findById(accountId)
                .orElseThrow(() -> {
                    log.debug("Couldn't find account with id: {}", accountId);
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.ACCOUNT_NOT_FOUND,
                            String.format("No account found with id %d", accountId));
                });
    }

    @Override
    public AccountModel save(AccountModel accountModel) {
        return accountRepository.save(accountModel);
    }

    @Override
    public Page<AccountModel> findAllBy(Specification<AccountModel> specification, Pageable pageable) {
        return accountRepository.findAll(specification, pageable);
    }
}
