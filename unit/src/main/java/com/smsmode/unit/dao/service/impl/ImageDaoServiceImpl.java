/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service.impl;

import com.smsmode.unit.dao.repository.ImageRepository;
import com.smsmode.unit.dao.service.ImageDaoService;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.model.ImageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageDaoServiceImpl implements ImageDaoService {

    private final ImageRepository imageRepository;

    @Override
    public boolean existsBy(Specification<ImageModel> specification) {
        return imageRepository.exists(specification);
    }

    @Override
    public ImageModel save(ImageModel image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteBy(Specification<ImageModel> specification) {
        imageRepository.delete(specification);
    }

    @Override
    public Page<ImageModel> findAllBy(Specification<ImageModel> specification, Pageable pageable) {
        return imageRepository.findAll(specification, pageable);
    }

    @Override
    public ImageModel findOneBy(Specification<ImageModel> specification) {
        return imageRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any image with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                            "No image found with the specified criteria");
                });
    }
}
