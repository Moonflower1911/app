package com.smsmode.task.controller.impl;

import com.smsmode.task.controller.IncidentImageController;
import com.smsmode.task.resource.image.ImageGetResource;
import com.smsmode.task.service.IncidentImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IncidentImageControllerImpl implements IncidentImageController {

    private final IncidentImageService incidentImageService;

    @Override
    public ResponseEntity<Page<ImageGetResource>> getImagesByIncidentId(String incidentId, Pageable pageable) {
        return incidentImageService.retrieveImages(incidentId, pageable);
    }

    @Override
    public ResponseEntity<Resource> getImageById(String imageId) {
        return incidentImageService.retrieveImage(imageId);
    }

    @Override
    public ResponseEntity<List<ImageGetResource>> postImagesForIncident(String incidentId, List<MultipartFile> files) {
        return incidentImageService.createImages(incidentId, files);
    }

    @Override
    public ResponseEntity<Void> deleteImageById(String imageId) {
        return incidentImageService.removeById(imageId);
    }

}
