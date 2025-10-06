/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.gateway.dao.service.impl;

import com.smsmode.gateway.dao.repository.TokenRepository;
import com.smsmode.gateway.dao.service.TokenDaoService;
import com.smsmode.gateway.model.TokenModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link TokenDaoService} interface, providing methods for managing {@link
 * TokenModel} entities in the persistence layer.
 *
 * <p>All operations are delegated to the {@link TokenRepository}, ensuring a separation of concerns
 * between the business logic and data access layers.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
@Service
@RequiredArgsConstructor
public class TokenDaoServiceImpl implements TokenDaoService {

  private final TokenRepository tokenRepository;

  /** {@inheritDoc}* */
  @Override
  public TokenModel save(TokenModel token) {
    return tokenRepository.save(token);
  }

  /** {@inheritDoc}* */
  @Override
  public boolean existsBy(Specification<TokenModel> specification) {
    return tokenRepository.exists(specification);
  }

}
