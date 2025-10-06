/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.controller.impl;

import com.smsmode.uaa.controller.UserController;
import com.smsmode.uaa.resource.user.UserItemGetResource;
import com.smsmode.uaa.resource.user.UserPatchResource;
import com.smsmode.uaa.resource.user.UserPostResource;
import com.smsmode.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Mar 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<Page<UserItemGetResource>> getAllByPage(String search, Pageable pageable) {
        return userService.retrieveUsersByPage(search, pageable);
    }

    @Override
    public ResponseEntity<UserItemGetResource> postUser(UserPostResource userPostResource) {
        return userService.createUser(userPostResource);
    }

    @Override
    public ResponseEntity<UserItemGetResource> patchUserById(String userId, UserPatchResource userPatchResource) {
        return userService.updateUserById(userId, userPatchResource);
    }
}
