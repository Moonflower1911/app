/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.dao.specification;

import com.smsmode.guest.model.SegmentModel;
import com.smsmode.guest.model.SegmentModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
public class SegmentSpecification {

    public static Specification<SegmentModel> withIdEqual(String segmentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(SegmentModel_.id), segmentId);
    }

    public static Specification<SegmentModel> withParent(Boolean withParent) {
        return (root, query, criteriaBuilder) -> {
            if (withParent == null) {
                return criteriaBuilder.conjunction();
            } else {
                return withParent ? criteriaBuilder.isNotNull(root.get(SegmentModel_.parent)) :
                        criteriaBuilder.isNull(root.get(SegmentModel_.parent));
            }
        };
    }

    public static Specification<SegmentModel> withEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get(SegmentModel_.enabled), enabled);
            }
        };
    }


    public static Specification<SegmentModel> withNameLike(String search) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(search) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(SegmentModel_.name)),
                                "%" + search.toLowerCase() + "%");
    }

    public static Specification<SegmentModel> withParentIdEqual(String parentId) {
        return (root, query, criteriaBuilder) -> {
            Join<SegmentModel, SegmentModel> parentJoin = root.join(SegmentModel_.parent);
            return criteriaBuilder.equal(parentJoin.get(SegmentModel_.id), parentId);
        };
    }
}
