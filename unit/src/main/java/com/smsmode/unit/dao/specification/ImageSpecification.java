/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.ImageModel;
import com.smsmode.unit.model.ImageModel_;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 20 May 2025</p>
 */
public class ImageSpecification {
    public static Specification<ImageModel> withUnitIdEqual(String unitId) {
        return (root, query, criteriaBuilder) -> {
            Join<ImageModel, UnitModel> join = root.join(ImageModel_.unit);
            return criteriaBuilder.equal(join.get(UnitModel_.id), unitId);
        };
    }

    public static Specification<ImageModel> withId(String imageId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ImageModel_.id), imageId);
    }

    public static Specification<ImageModel> withCover(boolean cover) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ImageModel_.cover), cover);
    }

    public static Specification<ImageModel> withUnit(UnitModel unit) {
        return (root, query, cb) -> cb.equal(root.get("unit"), unit);
    }

}
