/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.specification;

import com.smsmode.uaa.enumeration.TokenTypeEnum;
import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.TokenModel_;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Dec 2024</p>
 */
public class TokenSpecification {
    public static Specification<TokenModel> withEncryptedToken(String token) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TokenModel_.value), token);
    }

    public static Specification<TokenModel> withType(TokenTypeEnum tokenType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TokenModel_.type), tokenType);
    }
}
