/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.UserSpecification;
import com.smsmode.uaa.mappers.UserMapper;
import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.resource.user.UserItemGetResource;
import com.smsmode.uaa.resource.user.UserPatchResource;
import com.smsmode.uaa.resource.user.UserPostResource;
import com.smsmode.uaa.service.MailingService;
import com.smsmode.uaa.service.TokenService;
import com.smsmode.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Mar 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDaoService userDaoService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final MailingService mailingService;

    @Override
    public ResponseEntity<Page<UserItemGetResource>> retrieveUsersByPage(String search, Pageable pageable) {
        Specification<UserModel> specification = null;
        if (!ObjectUtils.isEmpty(search)) {
            specification = UserSpecification.withFullNameLike(search).or(UserSpecification.withEmailLike(search));
        }
        Page<UserModel> userModels = userDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(userModels.map(userMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<UserItemGetResource> createUser(UserPostResource userPostResource) {
        UserModel user = userMapper.postResourceToModel(userPostResource);
        user = userDaoService.save(user);
        log.debug("Will generate an activation key and it by email ...");
        TokenModel tokenModel = tokenService.generateAccountValidationToken(user);
        mailingService.sendActivationAccountEmail(user, tokenModel);
        return ResponseEntity.ok(userMapper.modelToGetResource(user));
    }

    @Override
    public ResponseEntity<UserItemGetResource> updateUserById(String userId, UserPatchResource userPatchResource) {
        UserModel existingUser = userDaoService.findOneBy(UserSpecification.withUuid(userId));
        existingUser = userMapper.patchResourceToModel(userPatchResource, existingUser);
        existingUser = userDaoService.save(existingUser);
        return ResponseEntity.ok(userMapper.modelToGetResource(existingUser));
    }
}
