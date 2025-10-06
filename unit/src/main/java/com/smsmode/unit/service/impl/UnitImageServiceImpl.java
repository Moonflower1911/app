/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.ImageDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.ImageSpecification;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.embeddable.MediaRefEmbeddable;
import com.smsmode.unit.exception.InternalServerException;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.mapper.ImageMapper;
import com.smsmode.unit.model.ImageModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.image.ImageGetResource;
import com.smsmode.unit.resource.image.ImagePatchResource;
import com.smsmode.unit.resource.image.MediaGetResource;
import com.smsmode.unit.service.UnitImageService;
import com.smsmode.unit.service.feign.MediaFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitImageServiceImpl implements UnitImageService {

    private final UnitDaoService unitDaoService;
    private final ImageDaoService imageDaoService;
    private final ImageMapper imageMapper;
    private final MediaFeignService mediaFeignService;

    @Value("${file.upload.unit-image}")
    public String unitImagePath;

    @Override
    public ResponseEntity<Page<ImageGetResource>> retrieveImages(String unitId, Pageable pageable) {
        Page<ImageModel> imageModels = imageDaoService.findAllBy(ImageSpecification.withUnitIdEqual(unitId), pageable);
        return ResponseEntity.ok(imageModels.map(imageMapper::modelToImageGetResource));
    }

    @Override
    public ResponseEntity<List<ImageGetResource>> createImage(String unitId, MultipartFile[] files) {

        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));

        if (files == null || files.length == 0) {
            throw new InternalServerException(
                    InternalServerExceptionTitleEnum.FILE_UPLOAD,
                    "No image files were provided.");
        }

        String filePath = unitImagePath.replace(":unitId", unitId);
        ResponseEntity<List<MediaGetResource>> mediaResponse = mediaFeignService.uploadMedia(filePath, files);
        List<MediaGetResource> mediaList = mediaResponse.getBody();

        if (mediaList == null || mediaList.isEmpty()) {
            throw new InternalServerException(
                    InternalServerExceptionTitleEnum.FILE_UPLOAD,
                    "Media upload failed or returned no files.");
        }

        boolean hasCover = imageDaoService.existsBy(ImageSpecification.withUnitIdEqual(unitId));
        List<ImageGetResource> savedImages = new ArrayList<>();

        for (int i = 0; i < mediaList.size(); i++) {
            MediaGetResource media = mediaList.get(i);

            ImageModel image = new ImageModel();
            image.setUnit(unit);
            image.setCover(!hasCover && i == 0);

            MediaRefEmbeddable mediaRef = new MediaRefEmbeddable();
            mediaRef.setUuid(media.getId());
            image.setMedia(mediaRef);

            image = imageDaoService.save(image);
            savedImages.add(imageMapper.modelToImageGetResource(image));
        }


        return ResponseEntity.created(URI.create("")).body(savedImages);
    }

    @Override
    public ResponseEntity<ImageGetResource> updateById(String imageId, ImagePatchResource imagePatchResource) {
        ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));

        if (imagePatchResource.isCover()) {
            UnitModel unit = image.getUnit();
            ImageModel existingCover = null;
            try {
                existingCover = imageDaoService.findOneBy(
                        ImageSpecification.withCover(true).and(ImageSpecification.withUnit(unit))
                );
            } catch (ResourceNotFoundException ignored) {
            }

            if (existingCover != null && !existingCover.getId().equals(image.getId())) {
                existingCover.setCover(false);
                imageDaoService.save(existingCover);
            }
            image.setCover(true);
        } else {
            image.setCover(false);
        }

        image = imageDaoService.save(image);
        return ResponseEntity.ok(imageMapper.modelToImageGetResource(image));
    }

    @Override
    public ResponseEntity<Void> removeById(String imageId) {
        if (imageDaoService.existsBy(ImageSpecification.withId(imageId))) {
            ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));
            String mediaId = image.getMedia().getUuid();
            try {
                mediaFeignService.deleteMediaById(mediaId);
            } catch (Exception e) {
                log.error("Failed to delete media in Media service. Aborting Unit metadata deletion.");
                throw new InternalServerException(
                        InternalServerExceptionTitleEnum.FILE_UPLOAD,
                        "Unable to delete image from media storage.");
            }
            imageDaoService.deleteBy(ImageSpecification.withId(imageId));
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                    "No image found with the specified criteria");
        }
    }

}
