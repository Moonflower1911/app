package com.smsmode.invoice.dao.service;

import com.smsmode.invoice.model.AccountClassModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AccountClassDaoService {

    AccountClassModel save(AccountClassModel accountClassModel);

    AccountClassModel findOneBy(Specification<AccountClassModel> specification);

    void delete(AccountClassModel accountClassModel);

    Page<AccountClassModel> findAllBy(Specification<AccountClassModel> specification, Pageable pageable);

    boolean existsBy(Specification<AccountClassModel> specification);
}

