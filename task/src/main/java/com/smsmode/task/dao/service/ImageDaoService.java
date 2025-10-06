package com.smsmode.task.dao.service;

import com.smsmode.task.model.ImageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ImageDaoService {

    boolean existsBy(Specification<ImageModel> specification);

    ImageModel save(ImageModel image);

    void deleteBy(Specification<ImageModel> specification);

    Page<ImageModel> findAllBy(Specification<ImageModel> specification, Pageable pageable);

    ImageModel findOneBy(Specification<ImageModel> specification);

    List<ImageModel> findAllBy(Specification<ImageModel> specification);

}
