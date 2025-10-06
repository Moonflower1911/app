package com.smsmode.pricing.dao.old.specification;

import com.smsmode.pricing.embeddable.SegmentRefEmbeddable;
import com.smsmode.pricing.embeddable.SegmentRefEmbeddable_;
import com.smsmode.pricing.embeddable.UnitRefEmbeddable_;
import com.smsmode.pricing.enumeration.RateTableTypeEnum;
import com.smsmode.pricing.model.old.OldRatePlanModel;
import com.smsmode.pricing.model.old.OldRatePlanModel_;
import com.smsmode.pricing.model.old.RateTableModel;
import com.smsmode.pricing.model.old.RateTableModel_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * Specification class for RatePlanModel queries.
 */
public class OldRatePlanSpecification {

    /**
     * Creates specification to search by rate plan name.
     */
    public static Specification<OldRatePlanModel> withNameContaining(String name) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(name) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(OldRatePlanModel_.name)),
                                "%" + name.toLowerCase() + "%"
                        );
    }

    /**
     * Finds rate plans that have at least one segment in common with the given segments.
     */
    public static Specification<OldRatePlanModel> withOverlappingSegments(Set<String> segmentUuids) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(segmentUuids)) {
                return criteriaBuilder.disjunction(); // Retourne false (aucun résultat)
            }

            Join<OldRatePlanModel, SegmentRefEmbeddable> segmentJoin = root.join(OldRatePlanModel_.segments);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(OldRatePlanModel_.enabled), true),
                    segmentJoin.get(SegmentRefEmbeddable_.id).in(segmentUuids)
            );
        };
    }

    /**
     * Creates specification to search by segment names in the Set.
     */
    public static Specification<OldRatePlanModel> withSegmentNamesContaining(String segmentName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(segmentName) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.join(OldRatePlanModel_.segments).get(SegmentRefEmbeddable_.name)),
                                "%" + segmentName.toLowerCase() + "%"
                        );
    }


    /**
     * Creates specification to find rate plans by unit UUID.
     */
    public static Specification<OldRatePlanModel> withUnitUuid(String unitUuid) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(unitUuid) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(OldRatePlanModel_.unit).get(UnitRefEmbeddable_.id), unitUuid);
    }

    public static Specification<OldRatePlanModel> withEnabled(Boolean b) {
        return (root, query, criteriaBuilder) ->
                b == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get(OldRatePlanModel_.enabled), b);
    }

    public static Specification<OldRatePlanModel> withNoSegments() {
        return (root, query, cb) -> cb.isEmpty(root.get(OldRatePlanModel_.segments));
    }

    public static Specification<OldRatePlanModel> withSegmentId(String segmentId) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(segmentId)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<OldRatePlanModel, SegmentRefEmbeddable> segmentsJoin = root.join(OldRatePlanModel_.segments, JoinType.LEFT);
                return criteriaBuilder.equal(segmentsJoin.get(SegmentRefEmbeddable_.id), segmentId);
            }
        };
    }

    public static Specification<OldRatePlanModel> withUnitId(String unitId) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(unitId) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(OldRatePlanModel_.unit).get(UnitRefEmbeddable_.id), unitId);
    }

    public static Specification<RateTableModel> withType(RateTableTypeEnum type) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(type) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get(RateTableModel_.type), type);
    }

    public static Specification<OldRatePlanModel> withStandard(boolean value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(OldRatePlanModel_.standard), value);
    }
}