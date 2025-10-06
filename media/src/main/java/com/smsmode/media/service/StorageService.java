package com.smsmode.media.service;

import com.smsmode.media.model.MediaModel;
import java.io.InputStream;

/**
 * Service interface for file storage operations in Media service.
 */
public interface StorageService {

    /**
     * Store a file at the specified path.
     */
    String storeFile(String path, InputStream inputStream);

    /**
     * Generate path for a media file.
     */
    String generatePath(MediaModel media, String customPath, boolean isRelative);

    void deleteFile(String path);
}