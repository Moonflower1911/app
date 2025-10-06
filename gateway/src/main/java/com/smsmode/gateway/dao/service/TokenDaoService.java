/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.gateway.dao.service;

import com.smsmode.gateway.model.TokenModel;
import org.springframework.data.jpa.domain.Specification;

/**
 * DAO (Data Access Object) service interface for managing {@link TokenModel} entities.
 *
 * <p>This interface provides methods and serves as an abstraction layer between the repository and
 * the business logic, ensuring a clear separation of concerns.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
public interface TokenDaoService {

  /**
   * Saves the given {@link TokenModel} instance to the persistence layer.
   *
   * @param token the {@link TokenModel} entity to be saved
   * @return the saved {@link TokenModel} instance
   */
  TokenModel save(TokenModel token);

  /**
   * Checks if a {@link TokenModel} entity exists that matches the given specification.
   *
   * @param specification the {@link Specification} defining the criteria for existence
   * @return {@code true} if an entity exists that matches the specification, {@code false}
   *     otherwise
   */
  boolean existsBy(Specification<TokenModel> specification);
}
