/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.gateway.dao.repository;

import com.smsmode.gateway.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link TokenModel} entities.
 *
 * <p>This repository extends {@link JpaRepository} to provide standard methods for data access and
 * management, and {@link JpaSpecificationExecutor} to support query specifications for more
 * complex, criteria-based queries.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
@Repository
public interface TokenRepository
    extends JpaRepository<TokenModel, String>, JpaSpecificationExecutor<TokenModel> {}
