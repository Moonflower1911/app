package com.smsmode.invoice.dao.service.impl;

import com.smsmode.invoice.dao.repository.PostingAccountsRepository;
import com.smsmode.invoice.dao.service.PostingAccountsDaoService;
import com.smsmode.invoice.exception.ResourceNotFoundException;
import com.smsmode.invoice.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.invoice.model.PostingAccountModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingAccountsDaoServiceImpl implements PostingAccountsDaoService {

    private final PostingAccountsRepository postingAccountsRepository;

    @Override
    public PostingAccountModel save(PostingAccountModel postingAccountModel) {
        return postingAccountsRepository.save(postingAccountModel);
    }

    @Override
    public PostingAccountModel findOneBy(Specification<PostingAccountModel> specification) {
        return postingAccountsRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any posting account with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.POSTING_ACCOUNT_NOT_FOUND,
                            "No posting account found with the specified criteria");
                });
    }

    @Override
    public void delete(PostingAccountModel postingAccountModel) {
        postingAccountsRepository.delete(postingAccountModel);
    }

    @Override
    public Page<PostingAccountModel> findAllBy(Specification<PostingAccountModel> specification, Pageable pageable) {
        return postingAccountsRepository.findAll(specification, pageable);
    }

    @Override
    public boolean existsBy(Specification<PostingAccountModel> spec) {
        return postingAccountsRepository.exists(spec);
    }
}
