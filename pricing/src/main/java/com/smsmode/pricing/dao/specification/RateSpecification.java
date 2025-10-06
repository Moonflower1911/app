/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public class RateSpecification {

    public static Specification<RateModel> withDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(RateModel_.date), startDate, endDate);
    }

    public static Specification<RateModel> withUnitIdIn(List<String> unitIds) {
        return (root, query, criteriaBuilder) -> {
            Join<RateModel, UnitRefModel> rateModelUnitRefModelJoin = root.join(RateModel_.unit);
            return criteriaBuilder.and(rateModelUnitRefModelJoin.get(UnitRefModel_.id).in(unitIds));
        };
    }

    public static Specification<RateModel> withRatePlanIdEqual(String ratePlanId) {
        return (root, query, criteriaBuilder) -> {
            Join<RateModel, RatePlanModel> rateModelRatePlanModelJoin = root.join(RateModel_.ratePlan);
            return criteriaBuilder.and(rateModelRatePlanModelJoin.get(RatePlanModel_.id).in(ratePlanId));
        };
    }

}
