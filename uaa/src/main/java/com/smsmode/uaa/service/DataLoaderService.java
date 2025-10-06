/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
public interface DataLoaderService {
    /**
     * Iterates through all roles defined in the {@link RoleEnum} enumeration and checks if each role
     * exists in the database using the {@link RoleDaoService}. If a role doesn't exist, it creates a
     * new {@link RoleModel} for that role and saves it to the database.
     */
    void createRoles();

    /**
     * Checks if there is an admin user with a specific email. If not, creates a new admin user.
     */
    void createAdminUser();
}
