package com.smsmode.invoice.dao.specification;

import com.smsmode.invoice.model.AccountClassModel;
import com.smsmode.invoice.model.AccountClassModel_;
import com.smsmode.invoice.model.PostingAccountModel;
import com.smsmode.invoice.model.PostingAccountModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class PostingAccountsSpecification {

    public static Specification<PostingAccountModel> withIdEqual(String postingAccountId) {
        return (root, query, cb) ->
                cb.equal(root.get(PostingAccountModel_.id), postingAccountId);
    }

    public static Specification<PostingAccountModel> withNameLike(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String likePattern = "%" + search.toLowerCase() + "%";
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(PostingAccountModel_.name)), likePattern);
    }

    public static Specification<PostingAccountModel> withCodeLike(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String likePattern = "%" + search.toLowerCase() + "%";
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(PostingAccountModel_.code)), likePattern);
    }

    public static Specification<PostingAccountModel> withCodeEqual(String code) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get(PostingAccountModel_.code)), code.toLowerCase());
    }

    public static Specification<PostingAccountModel> withAccountClassEqual(String accountClass) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(accountClass)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<PostingAccountModel, AccountClassModel> join = root.join(PostingAccountModel_.accountClass);
                return criteriaBuilder.equal(join.get(AccountClassModel_.name), accountClass);
            }
        };
    }

    public static Specification<PostingAccountModel> withEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> {
            if (enabled != null) {
                return criteriaBuilder.equal(root.get(PostingAccountModel_.enabled), enabled);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
