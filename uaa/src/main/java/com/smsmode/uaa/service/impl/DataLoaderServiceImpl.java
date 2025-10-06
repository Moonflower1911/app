/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service.impl;

import com.smsmode.uaa.dao.service.RoleDaoService;
import com.smsmode.uaa.dao.service.UserDaoService;
import com.smsmode.uaa.dao.specification.RoleSpecification;
import com.smsmode.uaa.dao.specification.UserSpecification;
import com.smsmode.uaa.enumeration.RoleEnum;
import com.smsmode.uaa.model.RoleModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.service.DataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService, CommandLineRunner {
    private final RoleDaoService roleDaoService;
    private final UserDaoService userDaoService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createRoles() {
        for (RoleEnum role : RoleEnum.values()) {
            log.debug("Will check if role: <{}> exists in database ...", role);
            if (roleDaoService.existsBy(RoleSpecification.withRole(role))) {
                log.info("Role <{}> does exists", role);
            } else {
                log.info("Role <{}> doesn't exist, will create it", role);
                RoleModel roleModel = new RoleModel(role);
                roleModel = roleDaoService.save(roleModel);
                log.info("Role <{}> created with Id: {}", role, roleModel.getId());
            }
        }
    }

    @Override
    public void createAdminUser() {
        if (!userDaoService.existsBy(UserSpecification.withEmail("hamzahabchi.dev@gmail.com"))) {
            log.debug("Root user <{}> doesn't exist, will create it", "hamzahabchi.dev@gmail.com");
            UserModel user = new UserModel();
            user.setFullName("Hamza HABCHI");
            user.setEmail("hamzahabchi.dev@gmail.com");
            user.setPassword(passwordEncoder.encode("theWitcher2014*"));
            user.setActivated(true);

            RoleModel adminRole = roleDaoService.findOneBy(RoleSpecification.withRole(RoleEnum.ADMINISTRATOR));
            user.setRoles(Stream.of(adminRole).collect(Collectors.toSet()));
            user = userDaoService.save(user);
            log.info("Root user <{}> created with Id: {}", user, user.getId());
        } else {
            log.info("Root user <{}> does exists", "hamzahabchi.dev@gmail.com");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.createRoles();
        this.createAdminUser();
    }
}
