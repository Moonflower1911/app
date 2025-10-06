package com.smsmode.invoice.dao.specification;

import com.smsmode.invoice.model.LedgerGroupModel;
import com.smsmode.invoice.model.LedgerGroupModel_;
import org.springframework.data.jpa.domain.Specification;

public class LedgerGroupSpecification {

    public static Specification<LedgerGroupModel> withIdEqual(String ledgerGroupId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(LedgerGroupModel_.id), ledgerGroupId);
    }

    public static Specification<LedgerGroupModel> withNameLike(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(LedgerGroupModel_.name)), "%" + search.toLowerCase() + "%");
    }

    public static Specification<LedgerGroupModel> withParent(Boolean withParent) {
        return (root, query, cb) -> {
            if (withParent == null) {
                return cb.conjunction();
            } else {
                return withParent
                        ? cb.isNotNull(root.get(LedgerGroupModel_.parent))
                        : cb.isNull(root.get(LedgerGroupModel_.parent));
            }
        };
    }

    public static Specification<LedgerGroupModel> withEnabled(Boolean enabled) {
        return (root, query, cb) -> {
            if (enabled == null) {
                return cb.conjunction();
            } else {
                return cb.equal(root.get(LedgerGroupModel_.enabled), enabled);
            }
        };
    }

    public static Specification<LedgerGroupModel> withParentId(String parentId) {
        return (root, query, cb) -> {
            if (parentId == null || parentId.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get(LedgerGroupModel_.parent).get("id"), parentId);
        };
    }

}
