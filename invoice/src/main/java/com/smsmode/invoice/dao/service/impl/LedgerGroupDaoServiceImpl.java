package com.smsmode.invoice.dao.service.impl;

import com.smsmode.invoice.dao.repository.LedgerGroupRepository;
import com.smsmode.invoice.dao.service.LedgerGroupDaoService;
import com.smsmode.invoice.exception.ResourceNotFoundException;
import com.smsmode.invoice.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.invoice.model.LedgerGroupModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LedgerGroupDaoServiceImpl implements LedgerGroupDaoService {

    private final LedgerGroupRepository ledgerGroupRepository;

    @Override
    public LedgerGroupModel save(LedgerGroupModel ledgerGroupModel) {
        return ledgerGroupRepository.save(ledgerGroupModel);
    }

    @Override
    public LedgerGroupModel findOneBy(Specification<LedgerGroupModel> specification) {
        return ledgerGroupRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any ledger group with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.LEDGER_GROUP_NOT_FOUND,
                            "No ledger group found with the specified criteria");
                });
    }

    @Override
    public void delete(LedgerGroupModel ledgerGroupModel) {
        ledgerGroupRepository.delete(ledgerGroupModel);
    }

    @Override
    public Page<LedgerGroupModel> findAllBy(Specification<LedgerGroupModel> specification, Pageable pageable) {
        return ledgerGroupRepository.findAll(specification, pageable);
    }

    @Override
    public long countByParentId(String parentId) {
        return ledgerGroupRepository.countByParent_Id(parentId);
    }

}
