package com.smsmode.invoice.dao.repository;

import com.smsmode.invoice.model.PostingAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostingAccountsRepository extends JpaRepository<PostingAccountModel, String>,
        JpaSpecificationExecutor<PostingAccountModel> {
}
