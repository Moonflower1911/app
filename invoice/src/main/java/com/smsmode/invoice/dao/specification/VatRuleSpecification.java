/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.dao.specification;

import com.smsmode.invoice.model.VatRuleModel;
import com.smsmode.invoice.model.VatRuleModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
public class VatRuleSpecification {
    public static Specification<VatRuleModel> withNameLike(String search) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(search)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get(VatRuleModel_.name), "%" + search + "%");
            }
        };
    }

    public static Specification<VatRuleModel> withIdEqual(String vatRuleId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(VatRuleModel_.id), vatRuleId);
    }
}
