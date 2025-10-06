package com.smsmode.media.dao.specification;

import com.smsmode.media.model.MediaModel;
import com.smsmode.media.model.MediaModel_;
import org.springframework.data.jpa.domain.Specification;

public class MediaSpecification {

    public static Specification<MediaModel> withIdEqual(String mediaId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(MediaModel_.id), mediaId);
    }

    public static Specification<MediaModel> withFileNameContaining(String fileName) {
        return (root, query, criteriaBuilder) -> {
            if (fileName == null || fileName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + fileName.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(MediaModel_.fileName)), likePattern);
        };
    }
}