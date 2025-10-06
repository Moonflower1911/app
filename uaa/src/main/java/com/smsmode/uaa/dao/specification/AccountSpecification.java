package com.smsmode.uaa.dao.specification;

import com.smsmode.uaa.model.AccountModel;
import com.smsmode.uaa.model.AccountModel_;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<AccountModel> withNameLike(String search) {
        return (root, query, cb)
                -> cb.like(root.get(AccountModel_.name), "%" + search + "%");
    }

}
