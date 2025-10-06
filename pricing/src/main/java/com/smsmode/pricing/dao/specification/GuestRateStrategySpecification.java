/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.GuestRateStrategyModel;
import com.smsmode.pricing.model.GuestRateStrategyModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Sep 2025</p>
 */
public class GuestRateStrategySpecification {

    public static Specification<GuestRateStrategyModel> withNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(GuestRateStrategyModel_.name)),
                        "%" + search.toLowerCase() + "%"
                );
            }
        };
    }

    public static Specification<GuestRateStrategyModel> withIdEqual(String guestRateStrategyId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GuestRateStrategyModel_.id), guestRateStrategyId);
    }

    public static Specification<GuestRateStrategyModel> withEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder.equal(root.get(GuestRateStrategyModel_.enabled), enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
