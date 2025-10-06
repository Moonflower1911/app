package com.smsmode.guest.dao.specification;

import com.smsmode.guest.embeddable.ContactEmbeddable_;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.model.PartyModel;
import com.smsmode.guest.model.PartyModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class PartySpecification {
    public static Specification<PartyModel> withIdEqual(String partyId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PartyModel_.id), partyId);
    }

    public static Specification<PartyModel> withFirstNameLike(String firstName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(firstName) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(PartyModel_.firstName)),
                        "%" + firstName.toLowerCase() + "%"
                );
    }

    public static Specification<PartyModel> withLastNameLike(String lastName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(lastName) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(PartyModel_.lastName)),
                        "%" + lastName.toLowerCase() + "%"
                );
    }

    public static Specification<PartyModel> withEmailLike(String email) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(email) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(PartyModel_.contact).get(ContactEmbeddable_.email)),
                        "%" + email.toLowerCase() + "%"
                );
    }

    public static Specification<PartyModel> withTypeEqual(PartyTypeEnum type) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(type) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(PartyModel_.type), type);
    }


}