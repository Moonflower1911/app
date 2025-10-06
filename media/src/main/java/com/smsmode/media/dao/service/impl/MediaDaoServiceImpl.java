package com.smsmode.media.dao.service.impl;

import com.smsmode.media.dao.repository.MediaRepository;
import com.smsmode.media.dao.service.MediaDaoService;
import com.smsmode.media.exception.ResourceNotFoundException;
import com.smsmode.media.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.media.model.MediaModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaDaoServiceImpl implements MediaDaoService {

    private final MediaRepository mediaRepository;

    @Override
    public boolean existsBy(Specification<MediaModel> specification) {
        return mediaRepository.exists(specification);
    }

    @Override
    public MediaModel save(MediaModel media) {
        return mediaRepository.save(media);
    }

    @Override
    public void deleteBy(Specification<MediaModel> specification) {
        mediaRepository.delete(specification);
    }

    @Override
    public Page<MediaModel> findAllBy(Specification<MediaModel> specification, Pageable pageable) {
        return mediaRepository.findAll(specification, pageable);
    }

    @Override
    public MediaModel findOneBy(Specification<MediaModel> specification) {
        return mediaRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any media with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.MEDIA_NOT_FOUND,
                            "No media found with the specified criteria");
                });
    }
}