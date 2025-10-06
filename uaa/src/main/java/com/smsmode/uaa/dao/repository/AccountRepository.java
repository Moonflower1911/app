package com.smsmode.uaa.dao.repository;

import com.smsmode.uaa.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository
        extends JpaRepository<AccountModel, Long>, JpaSpecificationExecutor<AccountModel> {
}
