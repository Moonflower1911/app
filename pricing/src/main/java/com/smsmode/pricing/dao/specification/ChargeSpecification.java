/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.ChargeModel;
import com.smsmode.pricing.model.ChargeModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Sep 2025</p>
 */
public class ChargeSpecification {
    public static Specification<ChargeModel> withNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(ChargeModel_.name)), "%" + search.toLowerCase() + "%");
            }
        };
    }

    public static Specification<ChargeModel> withCodeLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(ChargeModel_.code)), "%" + search.toLowerCase() + "%");
            }
        };
    }

    public static Specification<ChargeModel> withIdEqual(String chargeId) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(chargeId)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get(ChargeModel_.id), chargeId);
            }
        };
    }

    public static Specification<ChargeModel> withCodeEqual(String code) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(code)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get(ChargeModel_.code), code);
            }
        };
    }

    public static Specification<ChargeModel> withIdIn(Set<String> chargeIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(chargeIds)) {
                return criteriaBuilder.conjunction();
            } else {
                return root.get(ChargeModel_.id).in(chargeIds);
            }
        };
    }

    public static Specification<ChargeModel> withEnabledEqual(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder.equal(root.get(ChargeModel_.enabled), enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<ChargeModel> withIsExtra(Boolean isExtra) {
        return (root, query, criteriaBuilder) -> {
            if (isExtra != null) {
                return criteriaBuilder.equal(root.get(ChargeModel_.extraAllowed), isExtra);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<ChargeModel> withIsPackage(Boolean isPackage) {
        return (root, query, criteriaBuilder) -> {
            if (isPackage != null) {
                return criteriaBuilder.equal(root.get(ChargeModel_.packageAllowed), isPackage);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
