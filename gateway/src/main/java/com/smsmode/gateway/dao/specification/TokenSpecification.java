/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.gateway.dao.specification;

import com.smsmode.gateway.model.TokenModel;
import com.smsmode.gateway.model.TokenModel_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utility class providing specifications for querying {@link TokenModel} entities based on various
 * attributes. This class defines reusable specifications that can be used to filter {@link
 * TokenModel} instances in queries.
 *
 * <p>Specifications allow for defining criteria in a flexible and reusable manner, enabling
 * advanced query capabilities within the persistence layer.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Oct 2024
 */
public class TokenSpecification {

    /**
     * Creates a specification to find {@link TokenModel} entities by a specific token value.
     *
     * @param token the token value to match
     * @return a {@link Specification} for finding {@link TokenModel} instances with the given token
     */
    public static Specification<TokenModel> withToken(String token) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(TokenModel_.VALUE), token);
    }
}
