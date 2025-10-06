/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.service;

import com.smsmode.uaa.model.TokenModel;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Dec 2024
 */
public interface TokenDaoService {

    TokenModel findOneBy(Specification<TokenModel> specification);

    TokenModel save(TokenModel tokenModel);

    void delete(TokenModel tokenModel);
}
