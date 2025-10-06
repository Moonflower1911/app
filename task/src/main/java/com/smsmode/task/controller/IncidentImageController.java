package com.smsmode.task.controller;

import com.smsmode.task.resource.image.ImageGetResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/images")
public interface IncidentImageController {

    @GetMapping
    ResponseEntity<Page<ImageGetResource>> getImagesByIncidentId(@RequestParam("incidentId") String incidentId, Pageable pageable);

    @GetMapping("/{imageId}")
    ResponseEntity<Resource> getImageById(@PathVariable String imageId);

    @PostMapping
    ResponseEntity<List<ImageGetResource>> postImagesForIncident(
            @RequestParam("incidentId") String incidentId,
            @RequestParam("files") List<MultipartFile> files);

    @DeleteMapping("/{imageId}")
    ResponseEntity<Void> deleteImageById(@PathVariable String imageId);

}
