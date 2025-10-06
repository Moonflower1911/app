/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.specification;

import com.smsmode.pricing.model.UnitRefModel;
import com.smsmode.pricing.model.UnitRefModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public class UnitRefSpecification {
    public static Specification<UnitRefModel> withIdIn(Set<String> unitIds) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(unitIds)) {
                return criteriaBuilder.conjunction();
            } else {
                return root.get(UnitRefModel_.id).in(unitIds);
            }
        };
    }

    public static Specification<UnitRefModel> withIdEqual(String unitId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UnitRefModel_.id), unitId);
    }
}
