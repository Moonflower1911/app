/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.service.impl;

import com.smsmode.uaa.dao.repository.RoleRepository;
import com.smsmode.uaa.dao.service.RoleDaoService;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.uaa.model.RoleModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleDaoServiceImpl implements RoleDaoService {
    private final RoleRepository roleRepository;

    @Override
    public Boolean existsBy(Specification<RoleModel> specification) {
        return roleRepository.exists(specification);
    }

    @Override
    public RoleModel findOneBy(Specification<RoleModel> specification) {
        return roleRepository
                .findOne(specification)
                .orElseThrow(
                        () -> {
                            log.debug("Couldn't find any role with the specified criteria");
                            return new ResourceNotFoundException(
                                    ResourceNotFoundExceptionTitleEnum.ROLE_NOT_FOUND,
                                    "No role found with the specified criteria");
                        });
    }

    @Override
    public RoleModel save(RoleModel roleModel) {
        return roleRepository.save(roleModel);
    }
}
