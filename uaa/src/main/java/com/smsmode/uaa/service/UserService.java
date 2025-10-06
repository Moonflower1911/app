/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;

import com.smsmode.uaa.resource.user.UserItemGetResource;
import com.smsmode.uaa.resource.user.UserPatchResource;
import com.smsmode.uaa.resource.user.UserPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Mar 2025</p>
 */
public interface UserService {


    ResponseEntity<Page<UserItemGetResource>> retrieveUsersByPage(String search, Pageable pageable);

    ResponseEntity<UserItemGetResource> createUser(UserPostResource userPostResource);

    ResponseEntity<UserItemGetResource> updateUserById(String userId, UserPatchResource userPatchResource);

}
