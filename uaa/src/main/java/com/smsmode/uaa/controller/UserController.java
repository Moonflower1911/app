/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.controller;

import com.smsmode.uaa.resource.user.UserItemGetResource;
import com.smsmode.uaa.resource.user.UserPatchResource;
import com.smsmode.uaa.resource.user.UserPostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Mar 2025</p>
 */
@RequestMapping("/users")
public interface UserController {

    @GetMapping
    ResponseEntity<Page<UserItemGetResource>> getAllByPage(@RequestParam(required = false) String search, Pageable pageable);

    @PostMapping
    ResponseEntity<UserItemGetResource> postUser(@RequestBody @Valid UserPostResource userPostResource);

    @PatchMapping("/{userId}")
    ResponseEntity<UserItemGetResource> patchUserById(@PathVariable String userId, @RequestBody @Valid UserPatchResource userPatchResource);

}
