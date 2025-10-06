/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.image.ImageGetResource;
import com.smsmode.unit.resource.image.ImagePatchResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
@RequestMapping("/images")
public interface ImageController {

    @GetMapping
    ResponseEntity<Page<ImageGetResource>> getImageByUnitId(@RequestParam("unitId") String unitId, Pageable pageable);

    @PostMapping
    ResponseEntity<List<ImageGetResource>> postImageByUnitId(@RequestParam("unitId") String unitId, @RequestParam("files") MultipartFile[] files);

    @PatchMapping("/{imageId}")
    ResponseEntity<ImageGetResource> patchImageById(@PathVariable String imageId, @RequestBody ImagePatchResource imagePatchResource);

    @DeleteMapping("/{imageId}")
    ResponseEntity<Void> deleteImageById(@PathVariable String imageId);

}
