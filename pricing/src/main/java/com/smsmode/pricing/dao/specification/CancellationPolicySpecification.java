/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.CancellationPolicyModel;
import com.smsmode.pricing.model.CancellationPolicyModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
public class CancellationPolicySpecification {
    public static Specification<CancellationPolicyModel> withCodeEqual(String code) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CancellationPolicyModel_.code), code);
    }


    public static Specification<CancellationPolicyModel> withNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(CancellationPolicyModel_.name)), "%" + search.toLowerCase() + "%");
            }
        };
    }

    public static Specification<CancellationPolicyModel> withCodeLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(CancellationPolicyModel_.code)), "%" + search.toLowerCase() + "%");
            }
        };
    }

    public static Specification<CancellationPolicyModel> withEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder.equal(root.get(CancellationPolicyModel_.enabled), enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<CancellationPolicyModel> withId(String cancellationPolicyId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CancellationPolicyModel_.id), cancellationPolicyId);
    }
}
