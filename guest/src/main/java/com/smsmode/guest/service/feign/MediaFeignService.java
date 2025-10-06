/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.service.feign;

import com.smsmode.guest.resource.media.MediaGetResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jul 2025</p>
 */
@FeignClient(name = "media", path = "/medias")
public interface MediaFeignService {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<MediaGetResource>> uploadMedia(
            @RequestParam(value = "filePath", required = false) String filePath,
            @RequestPart("files") MultipartFile[] files
    );

    @DeleteMapping("/{mediaId}")
    ResponseEntity<Void> deleteMediaById(@PathVariable("mediaId") String mediaId);
}

