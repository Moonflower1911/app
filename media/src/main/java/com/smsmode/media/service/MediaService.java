package com.smsmode.media.service;

import com.smsmode.media.resource.media.MediaGetResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for Media business operations.
 */
public interface MediaService {

    /**
     * Upload multiple media files with optional custom filePath.
     */
    ResponseEntity<List<MediaGetResource>> uploadMedia(String filePath, MultipartFile[] files);

    /**
     * Get media information by ID.
     */
    ResponseEntity<MediaGetResource> getMediaById(String mediaId);

    /**
     * Get all media files with pagination and optional search.
     */
    ResponseEntity<Page<MediaGetResource>> getAllMedia(String search, Pageable pageable);

    ResponseEntity<Resource> getMediaFile(String mediaId);

    ResponseEntity<Void> removeById(String mediaId);
}