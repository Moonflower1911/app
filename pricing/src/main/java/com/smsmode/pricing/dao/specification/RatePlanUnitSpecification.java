/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.model.RatePlanModel_;
import com.smsmode.pricing.model.RatePlanUnitModel;
import com.smsmode.pricing.model.RatePlanUnitModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Sep 2025</p>
 */
public class RatePlanUnitSpecification {
    public static Specification<RatePlanUnitModel> withRatePlanIdEqual(String ratePlanId) {
        return (root, query, criteriaBuilder) -> {
            Join<RatePlanUnitModel, RatePlanModel> join = root.join(RatePlanUnitModel_.ratePlan);
            return criteriaBuilder.equal(join.get(RatePlanModel_.id), ratePlanId);
        };
    }

    public static Specification<RatePlanUnitModel> withIdEqual(String ratePlanUnitId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RatePlanUnitModel_.id), ratePlanUnitId);
    }
}
