/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.service;

import com.smsmode.uaa.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
public interface UserDaoService {

    UserModel findOneBy(Specification<UserModel> specification);

    UserModel save(UserModel loginModel);

    boolean existsBy(Specification<UserModel> specification);

    Page<UserModel> findAllBy(Specification<UserModel> specification, Pageable pageable);

}
