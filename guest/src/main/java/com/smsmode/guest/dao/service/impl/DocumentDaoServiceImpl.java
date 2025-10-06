package com.smsmode.guest.dao.service.impl;

import com.smsmode.guest.dao.repository.DocumentRepository;
import com.smsmode.guest.dao.service.DocumentDaoService;
import com.smsmode.guest.exception.ResourceNotFoundException;
import com.smsmode.guest.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.guest.model.DocumentModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Implementation of IdentificationDocumentDaoService.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentDaoServiceImpl implements DocumentDaoService {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentModel save(DocumentModel idDocument) {
        return documentRepository.save(idDocument);
    }

    @Override
    public DocumentModel findOneBy(Specification<DocumentModel> specification) {
        return documentRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any identification document with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.ID_DOCUMENT_NOT_FOUND,
                            "No identification document found with the specified criteria");
                });
    }

    @Override
    public Page<DocumentModel> findAllBy(Specification<DocumentModel> specification, Pageable pageable) {
        return documentRepository.findAll(specification, pageable);
    }

    @Override
    public boolean existsBy(Specification<DocumentModel> specification) {
        return documentRepository.exists(specification);
    }

    @Override
    public void deleteBy(Specification<DocumentModel> specification) {
        documentRepository.delete(specification);
    }

    @Override
    public void delete(DocumentModel idDocument) {
        documentRepository.delete(idDocument);
    }
}