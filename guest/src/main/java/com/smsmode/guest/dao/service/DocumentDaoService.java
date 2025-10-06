package com.smsmode.guest.dao.service;

import com.smsmode.guest.model.DocumentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * DAO service interface for IdentificationDocument operations.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
public interface DocumentDaoService {

    DocumentModel save(DocumentModel idDocument);

    DocumentModel findOneBy(Specification<DocumentModel> specification);

    Page<DocumentModel> findAllBy(Specification<DocumentModel> specification, Pageable pageable);

    boolean existsBy(Specification<DocumentModel> specification);

    void deleteBy(Specification<DocumentModel> specification);

    void delete(DocumentModel idDocument);
}