package com.smsmode.task.dao.specification;

import com.smsmode.task.model.IncidentModel;
import com.smsmode.task.model.IncidentModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class IncidentSpecification {

    public static Specification<IncidentModel> withNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(name) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(IncidentModel_.name)),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<IncidentModel> withIdEqual(String incidentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(IncidentModel_.id), incidentId);
    }


}
