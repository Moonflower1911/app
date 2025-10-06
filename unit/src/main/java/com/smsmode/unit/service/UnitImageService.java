/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.image.ImageGetResource;
import com.smsmode.unit.resource.image.ImagePatchResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
public interface UnitImageService {

    ResponseEntity<Page<ImageGetResource>> retrieveImages(String unitId, Pageable pageable);

    ResponseEntity<List<ImageGetResource>> createImage(String unitId, MultipartFile[] files);

    ResponseEntity<Void> removeById(String imageId);

    ResponseEntity<ImageGetResource> updateById(String imageId, ImagePatchResource imagePatchResource);

}
