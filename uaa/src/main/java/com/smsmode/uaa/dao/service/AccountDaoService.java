package com.smsmode.uaa.dao.service;

import com.smsmode.uaa.model.AccountModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AccountDaoService {

    AccountModel findOneBy(Specification<AccountModel> specification);

    AccountModel findById(Long accountId);

    AccountModel save(AccountModel accountModel);

    Page<AccountModel> findAllBy(Specification<AccountModel> specification, Pageable pageable);
}
