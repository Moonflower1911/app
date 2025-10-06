/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.specification;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.model.RoleModel;
import com.smsmode.uaa.model.RoleModel_;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.model.UserModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
public class UserSpecification {

    public static Specification<UserModel> withUuid(String uuid) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UserModel_.ID), uuid);
    }

    public static Specification<UserModel> withEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UserModel_.EMAIL), email);
    }

    public static Specification<UserModel> withEnabled(boolean value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UserModel_.ENABLED), value);
    }

    public static Specification<UserModel> withActive(boolean value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UserModel_.ACTIVATED), value);
    }

    public static Specification<UserModel> withEmailLike(String search) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(UserModel_.EMAIL), "%" + search + "%");
    }

    public static Specification<UserModel> withFullNameLike(String search) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(UserModel_.FULL_NAME), "%" + search + "%");
    }


    public static Specification<UserModel> withRoleIn(Set<RoleEnum> roles) {
        return CollectionUtils.isEmpty(roles) ? ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()) :
                ((root, query, criteriaBuilder) -> {
                    Join<UserModel, RoleModel> credentialModelRoleModelJoin = root.join(UserModel_.roles);
                    return criteriaBuilder.and(credentialModelRoleModelJoin.get(RoleModel_.NAME).in(roles));
                });
    }
    
}
