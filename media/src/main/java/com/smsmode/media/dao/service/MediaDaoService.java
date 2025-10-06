package com.smsmode.media.dao.service;

import com.smsmode.media.model.MediaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MediaDaoService {

    boolean existsBy(Specification<MediaModel> specification);

    MediaModel save(MediaModel media);

    void deleteBy(Specification<MediaModel> specification);

    Page<MediaModel> findAllBy(Specification<MediaModel> specification, Pageable pageable);

    MediaModel findOneBy(Specification<MediaModel> specification);
}