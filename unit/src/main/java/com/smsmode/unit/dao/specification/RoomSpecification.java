/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.model.RoomModel;
import com.smsmode.unit.model.RoomModel_;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Jun 2025</p>
 */
public class RoomSpecification {

    public static Specification<RoomModel> withIdEqual(String roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RoomModel_.id), roomId);
    }

    public static Specification<RoomModel> withUnitIdEqual(String unitId) {
        return (root, query, criteriaBuilder) -> {
            Join<RoomModel, UnitModel> join = root.join(RoomModel_.unit);
            return criteriaBuilder.equal(join.get(UnitModel_.id), unitId);
        };
    }

    public static Specification<RoomModel> withTypeEqual(RoomTypeEnum type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RoomModel_.type), type);
    }
}
