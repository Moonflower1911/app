package com.smsmode.invoice.dao.specification;

import com.smsmode.invoice.model.AccountClassModel;
import com.smsmode.invoice.model.AccountClassModel_;
import org.springframework.data.jpa.domain.Specification;

public class AccountClassSpecification {

    public static Specification<AccountClassModel> withIdEqual(String accountClassId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(AccountClassModel_.id), accountClassId);
    }

    public static Specification<AccountClassModel> withNameLike(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(AccountClassModel_.name)), "%" + search.toLowerCase() + "%");
    }

    public static Specification<AccountClassModel> withNameEqual(String name) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get(AccountClassModel_.name)), name.toLowerCase());
    }

    public static Specification<AccountClassModel> withEnabled(Boolean enabled) {
        return (root, query, cb) -> {
            if (enabled == null) {
                return cb.conjunction();
            } else {
                return cb.equal(root.get(AccountClassModel_.enabled), enabled);
            }
        };
    }

}
