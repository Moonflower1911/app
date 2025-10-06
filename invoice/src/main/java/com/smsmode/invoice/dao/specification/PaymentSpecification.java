package com.smsmode.invoice.dao.specification;

import com.smsmode.invoice.model.PaymentModel;
import com.smsmode.invoice.model.PaymentModel_;
import org.springframework.data.jpa.domain.Specification;

public class PaymentSpecification {

    public static Specification<PaymentModel> withIdEqual(String paymentId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PaymentModel_.id), paymentId);
    }

    public static Specification<PaymentModel> withReferenceLike(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(PaymentModel_.reference)), "%" + search.toLowerCase() + "%");
    }

}
