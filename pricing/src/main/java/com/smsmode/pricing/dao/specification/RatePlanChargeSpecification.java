/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 14 Sep 2025</p>
 */
public class RatePlanChargeSpecification {
    public static Specification<RatePlanChargeModel> withChargeNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<RatePlanChargeModel, ChargeModel> join = root.join(RatePlanChargeModel_.charge);
                return criteriaBuilder.like(criteriaBuilder.lower(join.get(ChargeModel_.name)),
                        "%".concat(search.toLowerCase()).concat("%"));
            }
        };
    }

    public static Specification<RatePlanChargeModel> withRatePlanNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<RatePlanChargeModel, RatePlanModel> join = root.join(RatePlanChargeModel_.ratePlan);
                return criteriaBuilder.like(criteriaBuilder.lower(join.get(RatePlanModel_.name)),
                        "%".concat(search.toLowerCase()).concat("%"));
            }
        };
    }

    public static Specification<RatePlanChargeModel> withEnabledEqual(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder.equal(root.get(RatePlanChargeModel_.enabled), enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }


    public static Specification<RatePlanChargeModel> withRatePlanIdEqual(String ratePlanId) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(ratePlanId)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<RatePlanChargeModel, RatePlanModel> join = root.join(RatePlanChargeModel_.ratePlan);
                return criteriaBuilder.equal(join.get(RatePlanModel_.id), ratePlanId);
            }
        };
    }

    public static Specification<RatePlanChargeModel> withIdEqual(String inclusionId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(RatePlanChargeModel_.id), inclusionId);
    }
}
