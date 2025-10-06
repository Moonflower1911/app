package com.smsmode.invoice.dao.specification;

import com.smsmode.invoice.embeddable.ClientRefEmbeddable_;
import com.smsmode.invoice.model.InvoiceModel;
import com.smsmode.invoice.model.InvoiceModel_;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class InvoiceSpecification {

    public static Specification<InvoiceModel> withReferenceLike(String reference) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(reference) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(InvoiceModel_.reference)),
                        "%" + reference.toLowerCase() + "%"
                );
    }

    public static Specification<InvoiceModel> withStatusLike(String status) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(status) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(InvoiceModel_.status)),
                        "%" + status.toLowerCase() + "%"
                );
    }

    public static Specification<InvoiceModel> withClientNameLike(String clientName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(clientName) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(InvoiceModel_.clientRef).get(ClientRefEmbeddable_.name)),
                        "%" + clientName.toLowerCase() + "%"
                );
    }

    public static Specification<InvoiceModel> withIdEqual(String invoiceId) {
        return (root, query, cb) -> cb.equal(root.get(InvoiceModel_.id), invoiceId);
    }

}
