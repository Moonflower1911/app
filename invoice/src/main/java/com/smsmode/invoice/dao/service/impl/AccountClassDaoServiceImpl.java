package com.smsmode.invoice.dao.service.impl;

import com.smsmode.invoice.dao.repository.AccountClassRepository;
import com.smsmode.invoice.dao.service.AccountClassDaoService;
import com.smsmode.invoice.exception.ResourceNotFoundException;
import com.smsmode.invoice.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.invoice.model.AccountClassModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountClassDaoServiceImpl implements AccountClassDaoService {

    private final AccountClassRepository accountClassRepository;

    @Override
    public AccountClassModel save(AccountClassModel accountClassModel) {
        return accountClassRepository.save(accountClassModel);
    }

    @Override
    public AccountClassModel findOneBy(Specification<AccountClassModel> specification) {
        return accountClassRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any account class with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.ACCOUNT_CLASS_NOT_FOUND,
                            "No account class found with the specified criteria");
                });
    }

    @Override
    public void delete(AccountClassModel accountClassModel) {
        accountClassRepository.delete(accountClassModel);
    }

    @Override
    public Page<AccountClassModel> findAllBy(Specification<AccountClassModel> specification, Pageable pageable) {
        return accountClassRepository.findAll(specification, pageable);
    }

    @Override
    public boolean existsBy(Specification<AccountClassModel> specification) {
        return accountClassRepository.count(specification) > 0;
    }

}
