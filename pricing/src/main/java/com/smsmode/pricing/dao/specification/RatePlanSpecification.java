/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.RatePlanModel;
import com.smsmode.pricing.model.RatePlanModel_;
import com.smsmode.pricing.model.RatePlanUnitModel_;
import com.smsmode.pricing.model.UnitRefModel_;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public class RatePlanSpecification {
    public static Specification<RatePlanModel> withIdEqual(String ratePlanId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RatePlanModel_.id), ratePlanId);
    }

    public static Specification<RatePlanModel> withNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(RatePlanModel_.name)),
                        "%" + search.toLowerCase() + "%"
                );
            }
        };
    }

    public static Specification<RatePlanModel> withCodeLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(RatePlanModel_.code)),
                        "%" + search.toLowerCase() + "%"
                );
            }
        };
    }

    public static Specification<RatePlanModel> withCodeEqual(String code) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(code) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(RatePlanModel_.code), code);
    }

    public static Specification<RatePlanModel> withUnitIdIn(Set<String> unitIds) {
        return (root, query, cb) -> {
            if (CollectionUtils.isEmpty(unitIds)) {
                return cb.conjunction();
            } else {
                query.distinct(true); // ensure no duplicates
                var rpuJoin = root.join(RatePlanModel_.ratePlanUnits, JoinType.INNER);
                var unitJoin = rpuJoin.join(RatePlanUnitModel_.unit, JoinType.INNER);
                return unitJoin.get(UnitRefModel_.id).in(unitIds);
            }
        };
    }

    public static Specification<RatePlanModel> withEnabledEqual(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder.equal(root.get(RatePlanModel_.enabled), enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };

    }

    public static Specification<RatePlanModel> withMinLosLessThanOrEqual(Integer numberOfNights) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(numberOfNights)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(RatePlanModel_.minLos), numberOfNights);
            }
        };
    }

    public static Specification<RatePlanModel> withMaxLosIsNullOrGreaterThanOrEqual(Integer numberOfNights) {
        return (root, query, criteriaBuilder) -> {
            if(ObjectUtils.isEmpty(numberOfNights)){
                return criteriaBuilder.conjunction();
            }else{
                return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get(RatePlanModel_.maxLos), numberOfNights),
                        criteriaBuilder.isNull(root.get(RatePlanModel_.maxLos)));
            }
        };
    }

    public static Specification<RatePlanModel> withMinLeadLessThanOrEqual(Integer numberOfDays) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(numberOfDays)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get(RatePlanModel_.minLead), numberOfDays);
            }
        };
    }

    public static Specification<RatePlanModel> withMaxLeadIsNullOrGreaterThanOrEqual(Integer numberOfDays) {
        return (root, query, criteriaBuilder) -> {
            if(ObjectUtils.isEmpty(numberOfDays)){
                return criteriaBuilder.conjunction();
            }else{
                return criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get(RatePlanModel_.maxLead), numberOfDays),
                        criteriaBuilder.isNull(root.get(RatePlanModel_.maxLead)));
            }
        };
    }
}
