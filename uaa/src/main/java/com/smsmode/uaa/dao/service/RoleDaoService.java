/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.service;

import com.smsmode.uaa.model.RoleModel;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
public interface RoleDaoService {

    Boolean existsBy(Specification<RoleModel> specification);

    RoleModel findOneBy(Specification<RoleModel> specification);

    RoleModel save(RoleModel roleModel);
}
