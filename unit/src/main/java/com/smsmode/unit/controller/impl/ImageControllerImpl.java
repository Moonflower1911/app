/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.ImageController;
import com.smsmode.unit.resource.image.ImageGetResource;
import com.smsmode.unit.resource.image.ImagePatchResource;
import com.smsmode.unit.service.UnitImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final UnitImageService unitImageService;

    @Override
    public ResponseEntity<Page<ImageGetResource>> getImageByUnitId(String unitId, Pageable pageable) {
        return unitImageService.retrieveImages(unitId, pageable);
    }

    @Override
    public ResponseEntity<List<ImageGetResource>> postImageByUnitId(String unitId, MultipartFile[] files) {
        return unitImageService.createImage(unitId, files);
    }

    @Override
    public ResponseEntity<ImageGetResource> patchImageById(String imageId, ImagePatchResource imagePatchResource) {
        return unitImageService.updateById(imageId, imagePatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteImageById(String imageId) {
        return unitImageService.removeById(imageId);
    }
}
