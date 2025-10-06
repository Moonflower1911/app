package com.smsmode.task.dao.service.Impl;

import com.smsmode.task.dao.repository.ImageRepository;
import com.smsmode.task.dao.service.ImageDaoService;
import com.smsmode.task.exception.ResourceNotFoundException;
import com.smsmode.task.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.task.model.ImageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ImageModel save(ImageModel imageModel) {
        return imageRepository.save(imageModel);
    }

    @Override
    public void deleteBy(Specification<ImageModel> specification) {
        imageRepository.deleteAll(imageRepository.findAll(specification));
    }

    @Override
    public Page<ImageModel> findAllBy(Specification<ImageModel> specification, Pageable pageable) {
        return imageRepository.findAll(specification, pageable);
    }

    @Override
    public ImageModel findOneBy(Specification<ImageModel> specification) {
        return imageRepository.findOne(specification).orElseThrow(() -> {
            log.debug("Couldn't find any image with the specified criteria");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                    "No image found with the specified criteria"
            );
        });
    }

    @Override
    public List<ImageModel> findAllBy(Specification<ImageModel> specification) {
        return imageRepository.findAll(specification);
    }

}
