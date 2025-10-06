package com.smsmode.invoice.dao.service;

import com.smsmode.invoice.model.LedgerGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface LedgerGroupDaoService {

    LedgerGroupModel save(LedgerGroupModel ledgerGroupModel);

    LedgerGroupModel findOneBy(Specification<LedgerGroupModel> specification);

    void delete(LedgerGroupModel ledgerGroupModel);

    Page<LedgerGroupModel> findAllBy(Specification<LedgerGroupModel> specification, Pageable pageable);

    long countByParentId(String parentId);
}
