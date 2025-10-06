package com.smsmode.invoice.dao.service;

import com.smsmode.invoice.model.PostingAccountModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PostingAccountsDaoService {

    PostingAccountModel save(PostingAccountModel postingAccountModel);

    PostingAccountModel findOneBy(Specification<PostingAccountModel> specification);

    void delete(PostingAccountModel postingAccountModel);

    Page<PostingAccountModel> findAllBy(Specification<PostingAccountModel> specification, Pageable pageable);

    boolean existsBy(Specification<PostingAccountModel> spec);
}
