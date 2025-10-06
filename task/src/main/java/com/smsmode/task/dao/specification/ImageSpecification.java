package com.smsmode.task.dao.specification;

import com.smsmode.task.model.ImageModel;
import com.smsmode.task.model.ImageModel_;
import com.smsmode.task.model.IncidentModel;
import com.smsmode.task.model.IncidentModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ImageSpecification {

    public static Specification<ImageModel> withIncidentIdEqual(String incidentId) {
        return (root, query, cb) -> {
            Join<ImageModel, IncidentModel> join = root.join(ImageModel_.incident);
            return cb.equal(join.get(IncidentModel_.id), incidentId);
        };
    }

    public static Specification<ImageModel> withId(String imageId) {
        return (root, query, cb) -> cb.equal(root.get(ImageModel_.id), imageId);
    }
}
