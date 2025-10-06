/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.dao.specification;

import com.smsmode.guest.model.DocumentModel;
import com.smsmode.guest.model.DocumentModel_;
import com.smsmode.guest.model.PartyModel;
import com.smsmode.guest.model.PartyModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * Specification class for IdentificationDocument entity queries.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
public class DocumentSpecification {

    public static Specification<DocumentModel> withPartyId(String partyId) {
        return (root, query, criteriaBuilder) -> {
            Join<DocumentModel, PartyModel> join = root.join(DocumentModel_.party);
            return criteriaBuilder.equal(join.get(PartyModel_.id), partyId);
        };
    }

    public static Specification<DocumentModel> withId(String documentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(DocumentModel_.id), documentId);
    }

    public static Specification<DocumentModel> withValueLike(String value) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(value) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(DocumentModel_.value)),
                                "%".concat(value.toLowerCase()).concat("%"));

    }

}