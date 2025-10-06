package com.smsmode.task.service.impl;

import com.smsmode.task.dao.service.ImageDaoService;
import com.smsmode.task.dao.service.IncidentDaoService;
import com.smsmode.task.dao.specification.ImageSpecification;
import com.smsmode.task.dao.specification.IncidentSpecification;
import com.smsmode.task.exception.InternalServerException;
import com.smsmode.task.exception.ResourceNotFoundException;
import com.smsmode.task.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.task.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.task.mapper.ImageMapper;
import com.smsmode.task.model.ImageModel;
import com.smsmode.task.model.IncidentModel;
import com.smsmode.task.resource.image.ImageGetResource;
import com.smsmode.task.service.IncidentImageService;
import com.smsmode.task.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentImageServiceImpl implements IncidentImageService {

    private final IncidentDaoService incidentDaoService;
    private final ImageDaoService imageDaoService;
    private final StorageService storageService;
    private final ImageMapper imageMapper;

    @Override
    public ResponseEntity<Page<ImageGetResource>> retrieveImages(String incidentId, Pageable pageable) {
        Page<ImageModel> imageModels = imageDaoService.findAllBy(ImageSpecification.withIncidentIdEqual(incidentId), pageable);
        return ResponseEntity.ok(imageModels.map(imageMapper::modelToImageGetResource));
    }

    @Override
    public ResponseEntity<Resource> retrieveImage(String imageId) {
        ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));

        String imagePath = storageService.generateIncidentImagePath(image);
        File file = new File(imagePath);

        if (!file.exists()) {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                    "No image found with the specified criteria");
        }

        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            Resource resource = new InputStreamResource(new ByteArrayInputStream(bytes));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/*")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + image.getFileName())
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                    .body(resource);
        } catch (IOException e) {
            log.warn("Error converting image file to stream", e);
            throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "Error while converting file to stream");
        }
    }

    @Override
    public ResponseEntity<List<ImageGetResource>> createImages(String incidentId, List<MultipartFile> files) {
        IncidentModel incident = incidentDaoService.findOneBy(IncidentSpecification.withIdEqual(incidentId));
        List<ImageGetResource> result = new ArrayList<>();

        for (MultipartFile file : files) {
            ImageModel image = new ImageModel();
            image.setFileName(file.getOriginalFilename());
            image.setIncident(incident);

            image = imageDaoService.save(image);

            String imagePath = storageService.generateIncidentImagePath(image);

            try {
                String imageFileName = storageService.storeFile(imagePath, file.getInputStream());
                if (ObjectUtils.isEmpty(imageFileName)) {
                    imageDaoService.deleteBy(ImageSpecification.withId(image.getId()));
                    throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "An error occurred while saving the image.");
                }
            } catch (IOException e) {
                log.warn("Error storing image file", e);
                imageDaoService.deleteBy(ImageSpecification.withId(image.getId()));
                throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "An error occurred while saving the image.");
            }

            result.add(imageMapper.modelToImageGetResource(image));
        }

        return ResponseEntity.created(URI.create("")).body(result);
    }

    @Override
    public ResponseEntity<Void> removeById(String imageId) {
        if (imageDaoService.existsBy(ImageSpecification.withId(imageId))) {
            ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));
            String imagePath = storageService.generateIncidentImagePath(image);
            storageService.deleteFile(imagePath);
            imageDaoService.deleteBy(ImageSpecification.withId(imageId));
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                    "No image found with the specified criteria");
        }
    }
}
