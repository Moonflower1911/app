/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.UserSpecification;
import com.smsmode.uaa.exception.AuthorizationForbiddenException;
import com.smsmode.uaa.exception.ResourceNotFoundException;
import com.smsmode.uaa.exception.enumeration.AuthorizationForbiddenExceptionTitleEnum;
import com.smsmode.uaa.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.service.LocalUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalUserDetailsServiceImpl implements LocalUserDetailsService {
    private final UserDaoService userDaoService;

    /**
     * Loads user details by username from the local database.
     *
     * @param username The username of the user to load.
     * @return A {@link UserDetails} object representing the loaded user.
     * @throws UsernameNotFoundException if the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                Optional.ofNullable(userDaoService.findOneBy(UserSpecification.withEmail(username)))
                        .map(this::createSpringSecurityUser)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                ResourceNotFoundExceptionTitleEnum.USER_NOT_FOUND,
                                                "User with email " + username + " was not found in the database"));
    }

    /**
     * Creates a Spring Security user from the given {@link UserModel}.
     *
     * @param userModel The user entity from the database.
     * @return A {@link UserDetails} object representing the user.
     */
    private UserDetails createSpringSecurityUser(UserModel userModel) {
        if (!userModel.isEnabled()) {
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.USER_DISABLED, "Your account is disabled. Please contact support.");
        }
        if (!userModel.isActivated()) {
            throw new AuthorizationForbiddenException(
                    AuthorizationForbiddenExceptionTitleEnum.USER_NOT_ACTIVATED, "Your account is not yet validated. Please check your email for validation instructions.");
        }
/*        if (CredentialsStateEnum.LOCKED.equals(userModel.getCredentialsState())) {
            throw new TooManyRequestException(TooManyRequestExceptionTitleEnum.MAX_LOGIN_ATTEMPT, "Too many failed login attempts. Please wait before trying again.");
        }*/
        Set<GrantedAuthority> grantedAuthorities =
                userModel.getRoles().stream()
                        .map(roleDomain -> new SimpleGrantedAuthority(roleDomain.getAuthority()))
                        .collect(Collectors.toSet());

        return new User(userModel.getEmail(), userModel.getPassword(), grantedAuthorities);
    }
}
