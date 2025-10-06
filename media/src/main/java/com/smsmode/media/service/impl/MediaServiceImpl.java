package com.smsmode.media.service.impl;

import com.smsmode.media.dao.service.MediaDaoService;
import com.smsmode.media.dao.specification.MediaSpecification;
import com.smsmode.media.exception.InternalServerException;
import com.smsmode.media.exception.ResourceNotFoundException;
import com.smsmode.media.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.media.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.media.mapper.MediaMapper;
import com.smsmode.media.model.MediaModel;
import com.smsmode.media.resource.media.MediaGetResource;
import com.smsmode.media.service.MediaService;
import com.smsmode.media.service.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of MediaService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaDaoService mediaDaoService;
    private final MediaMapper mediaMapper;
    private final StorageService storageService;

    @Value("${file.upload.media}")
    private String mediaUploadBasePath;

    @Override
    @Transactional
    public ResponseEntity<List<MediaGetResource>> uploadMedia(String filePath, MultipartFile[] files) {

        if (files == null || files.length == 0) {
            throw new InternalServerException(
                    InternalServerExceptionTitleEnum.FILE_PROCESSING,
                    "No files provided for upload"
            );
        }

        List<MediaGetResource> uploadedMedia = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                try {
                    // 1. Créer MediaModel avec métadonnées extraites du fichier
                    MediaModel mediaModel = createMediaModelFromFile(file);

                    // 2. Sauvegarder en base pour obtenir l'ID
                    mediaModel = mediaDaoService.save(mediaModel);

                    // 2. Générer le chemin relatif pour stockage en BD
                    String relativePath = storageService.generatePath(mediaModel, filePath, true);

                    // 3. Mettre à jour le filePath dans le modèle
                    mediaModel.setFilePath(relativePath);  // ← Stocker le relativePath
                    mediaModel = mediaDaoService.save(mediaModel);

                    // 4. Générer le chemin de stockage complet
                    String fullStoragePath = storageService.generatePath(mediaModel, filePath, false);

                    // 5. Sauvegarder le fichier physique
                    String savedFileName = storageService.storeFile(fullStoragePath, file.getInputStream());

                    if (ObjectUtils.isEmpty(savedFileName)) {
                        // Rollback: supprimer l'enregistrement en cas d'échec
                        mediaDaoService.deleteBy(MediaSpecification.withIdEqual(mediaModel.getId()));
                        throw new InternalServerException(
                                InternalServerExceptionTitleEnum.FILE_UPLOAD,
                                "Failed to save media file: " + file.getOriginalFilename()
                        );
                    }

                    // 6. Ajouter à la liste des médias créés
                    uploadedMedia.add(mediaMapper.modelToGetResource(mediaModel));

                } catch (IOException e) {
                    log.error("Error processing file: {}", file.getOriginalFilename(), e);
                    throw new InternalServerException(
                            InternalServerExceptionTitleEnum.FILE_UPLOAD,
                            "Failed to process file: " + file.getOriginalFilename()
                    );
                }
            }
        }

        return ResponseEntity.created(URI.create("")).body(uploadedMedia);
    }

    @Override
    public ResponseEntity<MediaGetResource> getMediaById(String mediaId) {
        MediaModel media = mediaDaoService.findOneBy(MediaSpecification.withIdEqual(mediaId));
        return ResponseEntity.ok(mediaMapper.modelToGetResource(media));
    }

    @Override
    public ResponseEntity<Page<MediaGetResource>> getAllMedia(String search, Pageable pageable) {
        Specification<MediaModel> spec = Specification.where(null);

        if (search != null && !search.trim().isEmpty()) {
            spec = MediaSpecification.withFileNameContaining(search);
        }

        Page<MediaModel> mediaModels = mediaDaoService.findAllBy(spec, pageable);
        return ResponseEntity.ok(mediaModels.map(mediaMapper::modelToGetResource));
    }

    /**
     * Crée un MediaModel à partir des métadonnées du fichier.
     */
    private MediaModel createMediaModelFromFile(MultipartFile file) {
        MediaModel mediaModel = new MediaModel();

        // Extraire les métadonnées du fichier
        String originalFileName = file.getOriginalFilename();
        mediaModel.setFileName(originalFileName);
        mediaModel.setSize(file.getSize());
        mediaModel.setType(file.getContentType());

        // Extraire l'extension
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        mediaModel.setExtension(extension);

        // Définir un filePath temporaire pour éviter la contrainte NOT NULL
        mediaModel.setFilePath("temp");

        return mediaModel;
    }


    @Override
    public ResponseEntity<Resource> getMediaFile(String mediaId) {
        MediaModel media = mediaDaoService.findOneBy(MediaSpecification.withIdEqual(mediaId));

        if (media == null || media.getFilePath() == null) {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.MEDIA_NOT_FOUND,
                    "No image found for media ID: " + mediaId);
        }

        // Combine base path with relative file path stored in DB
        String imagePath = mediaUploadBasePath + "/" + media.getFilePath();
        File file = new File(imagePath);

        if (file.exists()) {
            log.debug("File exists: {}", imagePath);
            Resource resource;
            try {
                byte[] bytes = FileUtils.readFileToByteArray(file);
                resource = new InputStreamResource(new ByteArrayInputStream(bytes));
            } catch (IOException e) {
                log.debug("Failed to convert file to byte stream: {}", imagePath, e);
                throw new InternalServerException(
                        InternalServerExceptionTitleEnum.FILE_UPLOAD,
                        "An error occurred while converting the file to a byte stream.");
            }

            String contentType;
            try {
                contentType = Files.probeContentType(file.toPath());
            } catch (IOException e) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + media.getFileName())
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                    .body(resource);
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.MEDIA_NOT_FOUND,
                    "Stored image file does not exist on disk for media ID: " + mediaId);
        }
    }

    @Override
    public ResponseEntity<Void> removeById(String mediaId) {
        if (mediaDaoService.existsBy(MediaSpecification.withIdEqual(mediaId))) {
            MediaModel media = mediaDaoService.findOneBy(MediaSpecification.withIdEqual(mediaId));
            String mediaPath = mediaUploadBasePath + "/" + media.getFilePath();
            storageService.deleteFile(mediaPath);
            mediaDaoService.deleteBy(MediaSpecification.withIdEqual(mediaId));
            return ResponseEntity.noContent().build();
        }else{
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.MEDIA_NOT_FOUND,
                    "No image found with the specified criteria");
        }
    }

}