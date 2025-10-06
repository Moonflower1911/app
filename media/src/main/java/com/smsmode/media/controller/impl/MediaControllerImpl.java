package com.smsmode.media.controller.impl;

import com.smsmode.media.controller.MediaController;
import com.smsmode.media.resource.media.MediaGetResource;
import com.smsmode.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.core.io.Resource;

/**
 * Implementation of MediaController.
 */
@RestController
@RequiredArgsConstructor
public class MediaControllerImpl implements MediaController {

    private final MediaService mediaService;

    @Override
    public ResponseEntity<List<MediaGetResource>> uploadMedia(String filePath, MultipartFile[] files) {
        return mediaService.uploadMedia(filePath, files);
    }

    @Override
    public ResponseEntity<?> getMedia(String mediaId, boolean file) {
        if (file) {
            return mediaService.getMediaFile(mediaId);
        } else {
            return mediaService.getMediaById(mediaId);
        }
    }

    @Override
    public ResponseEntity<Page<MediaGetResource>> getAllMedia(String search, Pageable pageable) {
        return mediaService.getAllMedia(search, pageable);
    }


    @Override
    public ResponseEntity<Void> deleteMediaById(String mediaId){
        return mediaService.removeById(mediaId);
    }
}