/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.service.impl;

import com.smsmode.uaa.dao.repository.TokenRepository;
import com.smsmode.uaa.dao.service.TokenDaoService;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.uaa.model.TokenModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Dec 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenDaoServiceImpl implements TokenDaoService {

    private final TokenRepository tokenRepository;

    @Override
    public TokenModel findOneBy(Specification<TokenModel> specification) {
        return tokenRepository
                .findOne(specification)
                .orElseThrow(
                        () -> {
                            log.debug("Couldn't find any token with the specified criteria");
                            return new ResourceNotFoundException(
                                    ResourceNotFoundExceptionTitleEnum.USER_NOT_FOUND,
                                    "No token found with the specified criteria");
                        });
    }

    @Override
    public TokenModel save(TokenModel tokenModel) {
        return tokenRepository.save(tokenModel);
    }

    @Override
    public void delete(TokenModel tokenModel) {
        tokenRepository.delete(tokenModel);
    }
}
