package com.smsmode.invoice.dao.repository;

import com.smsmode.invoice.model.LedgerGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LedgerGroupRepository extends JpaRepository<LedgerGroupModel, String>,
        JpaSpecificationExecutor<LedgerGroupModel> {

    long countByParent_Id(String parentId);

}
