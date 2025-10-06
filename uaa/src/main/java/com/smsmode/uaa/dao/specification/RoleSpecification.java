/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.specification;

import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.model.RoleModel;
import com.smsmode.uaa.model.RoleModel_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
public class RoleSpecification {
    /**
     * Creates a specification to query {@link RoleModel} by matching its name with the provided
     * role.
     *
     * @param role The role to match.
     * @return A {@link Specification} for querying {@link RoleModel} by role name.
     */
    public static Specification<RoleModel> withRole(RoleEnum role) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal((root.get(RoleModel_.name)), role));
    }

    /**
     * Creates a specification to query {@link RoleModel} by matching the name within a provided roles list
     *
     * @param roles The roles list
     * @return A {@link Specification} for querying {@link RoleModel} in the provided list.
     */
    public static Specification<RoleModel> withRoleIn(Set<RoleEnum> roles) {
        return (root, query, criteriaBuilder) -> root.get(RoleModel_.name).in(roles);
    }

}
