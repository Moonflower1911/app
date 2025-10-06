package com.smsmode.task.service;

import com.smsmode.task.resource.image.ImageGetResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IncidentImageService {

    ResponseEntity<Page<ImageGetResource>> retrieveImages(String incidentId, Pageable pageable);

    ResponseEntity<Resource> retrieveImage(String imageId);

    ResponseEntity<List<ImageGetResource>> createImages(String incidentId, List<MultipartFile> files);

    ResponseEntity<Void> removeById(String imageId);
}
