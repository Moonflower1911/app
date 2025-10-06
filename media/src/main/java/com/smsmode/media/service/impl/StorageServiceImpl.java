package com.smsmode.media.service.impl;

import com.smsmode.media.model.MediaModel;
import com.smsmode.media.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Implementation of StorageService for Media service file operations.
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Value("${file.upload.media}")
    private String mediaUploadBasePath;

    @Override
    public String storeFile(String path, InputStream inputStream) {
        Path filePath = Paths.get(path);
        try {
            // Créer les répertoires parents si ils n'existent pas
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
                log.debug("Created directories: {}", filePath.getParent());
            }

            // Copier le fichier
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("File stored successfully at: {}", path);
            return filePath.getFileName().toString();

        } catch (IOException e) {
            log.error("Failed to store file at path: {}", path, e);
            return null;
        }
    }

    @Override
    public String generatePath(MediaModel media, String customPath, boolean isRelative) {
        // Construction du chemin selon la logique
        StringBuilder pathBuilder = new StringBuilder();

        if (!isRelative) {
            // Pour fullPath : ajouter le basePath au début
            pathBuilder.append(mediaUploadBasePath).append("/");
        }

        if (StringUtils.hasText(customPath)) {
            // Cas avec customPath
            pathBuilder.append(customPath.trim())
                    .append("/")
                    .append(media.getId())
                    .append(media.getExtension());
        } else {
            // Cas sans customPath (appel direct)
            pathBuilder.append(media.getId())
                    .append(media.getExtension());
        }

        String generatedPath = pathBuilder.toString();
        log.debug("Generated {} path: {}", isRelative ? "relative" : "full", generatedPath);

        return generatedPath;
    }

    @Override
    public void deleteFile(String path) {
        File file = new File(path);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                log.info("File: '{}' delete successfully.", path);
            } else {
                log.warn("File: '{}' deletion failed.", path);
            }
        } else {
            log.warn("File: '{}' does not exists.", path);
        }
    }

}