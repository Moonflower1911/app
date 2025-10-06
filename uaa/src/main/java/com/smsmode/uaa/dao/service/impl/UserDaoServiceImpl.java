/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.dao.service.impl;

import com.smsmode.uaa.dao.repository.UserRepository;
import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.uaa.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class UserDaoServiceImpl implements UserDaoService {
    private final UserRepository userRepository;

    @Override
    public UserModel findOneBy(Specification<UserModel> specification) {
        return userRepository
                .findOne(specification)
                .orElseThrow(
                        () -> {
                            log.debug("Couldn't find any user with the specified criteria");
                            return new ResourceNotFoundException(
                                    ResourceNotFoundExceptionTitleEnum.USER_NOT_FOUND,
                                    "No user found with the specified criteria");
                        });
    }

    @Override
    public UserModel save(UserModel credentialModel) {
        return userRepository.save(credentialModel);
    }

    @Override
    public boolean existsBy(Specification<UserModel> specification) {
        return userRepository.exists(specification);
    }

    @Override
    public Page<UserModel> findAllBy(
            Specification<UserModel> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }
}
