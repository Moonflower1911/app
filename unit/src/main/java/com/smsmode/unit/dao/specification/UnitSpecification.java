package com.smsmode.unit.dao.specification;

import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class UnitSpecification {

    public static Specification<UnitModel> withIdEqual(String unitId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UnitModel_.id), unitId);
    }

    public static Specification<UnitModel> withNameLike(String unitName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(unitName) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(UnitModel_.name)),
                                "%" + unitName.toLowerCase() + "%");
    }

    public static Specification<UnitModel> withSubTitleLike(String subTitle) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(subTitle) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(UnitModel_.subTitle)),
                                "%" + subTitle.toLowerCase() + "%");
    }


    public static Specification<UnitModel> withNature(UnitNatureEnum nature) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(nature) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(UnitModel_.nature), nature);
    }

    public static Specification<UnitModel> withParent(Boolean withParent) {
        return (root, query, criteriaBuilder) ->
                withParent == null ? criteriaBuilder.conjunction() :
                        ((withParent.booleanValue()) ? criteriaBuilder.isNotNull(root.get(UnitModel_.parent)) :
                                criteriaBuilder.isNull(root.get(UnitModel_.parent)));
    }

    public static Specification<UnitModel> withParentFilter(Boolean withParent) {
        return (root, query, criteriaBuilder) -> {
            if (withParent == null) {
                return criteriaBuilder.conjunction();
            }
            if (withParent) {
                return criteriaBuilder.isNotNull(root.get(UnitModel_.parent));
            } else {
                return criteriaBuilder.isNull(root.get(UnitModel_.parent));
            }
        };
    }

    public static Specification<UnitModel> withParentUnitId(String parentId) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(parentId)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<UnitModel, UnitModel> unitJoin = root.join(UnitModel_.parent);
                return criteriaBuilder.equal(unitJoin.get(UnitModel_.id), parentId);
            }
        };
    }

    public static Specification<UnitModel> withReadiness(boolean readiness) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UnitModel_.readiness), readiness);
    }
}
