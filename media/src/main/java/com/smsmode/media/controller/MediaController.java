package com.smsmode.media.controller;

import com.smsmode.media.resource.media.MediaGetResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST Controller for Media operations.
 */
@RequestMapping("/medias")
public interface MediaController {

    /**
     * Upload multiple files with optional filePath.
     * If filePath is provided, files will be stored in /media/{filePath}/{uuid}.{extension}
     * If filePath is not provided, files will be stored in /media/{uuid}.{extension}
     */
    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<List<MediaGetResource>> uploadMedia(
            @RequestParam(value = "filePath", required = false) String filePath,
            @RequestParam("files") MultipartFile[] files);

    /**
     * Get media by ID.
     */
    @GetMapping("/{mediaId}")
    ResponseEntity<?> getMedia(@PathVariable("mediaId") String mediaId,
                               @RequestParam(value = "file", defaultValue = "false") boolean file);

    /**
     * Get all media files with pagination and optional search.
     */
    @GetMapping
    ResponseEntity<Page<MediaGetResource>> getAllMedia(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable);

    @DeleteMapping("/{mediaId}")
    ResponseEntity<Void> deleteMediaById(@PathVariable String mediaId);

}