package com.smsmode.invoice.dao.repository;

import com.smsmode.invoice.model.AccountClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountClassRepository extends JpaRepository<AccountClassModel, String>,
        JpaSpecificationExecutor<AccountClassModel> {
}
