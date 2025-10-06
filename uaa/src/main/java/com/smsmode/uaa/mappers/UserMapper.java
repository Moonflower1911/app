/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.mappers;

import com.smsmode.uaa.dao.service.RoleDaoService;
import com.smsmode.uaa.dao.specification.RoleSpecification;
import com.smsmode.uaa.model.RoleModel;
import com.smsmode.uaa.model.UserModel;
import com.smsmode.uaa.model.base.AbstractIntBaseModel;
import com.smsmode.uaa.model.base.AbstractUuidBaseModel;
import com.smsmode.uaa.resource.common.AuditGetResource;
import com.smsmode.uaa.resource.user.UserItemGetResource;
import com.smsmode.uaa.resource.user.UserPatchResource;
import com.smsmode.uaa.resource.user.UserPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 Mar 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {

    private RoleDaoService roleDaoService;

    @Autowired
    public void setRoleDaoService(RoleDaoService roleDaoService) {
        this.roleDaoService = roleDaoService;
    }

    @Mapping(target = "roles", ignore = true)
    public abstract UserItemGetResource modelToGetResource(UserModel userModel);

    @AfterMapping
    public void afterMapping(UserModel userModel, @MappingTarget UserItemGetResource userItemGetResource) {
        userItemGetResource.setAudit(this.modelToAuditResource(userModel));
        userItemGetResource.setRoles(userModel.getRoles().stream().map(RoleModel::getName).collect(Collectors.toSet()));
    }

    public abstract AuditGetResource modelToAuditResource(AbstractUuidBaseModel baseModel);

    public abstract UserModel postResourceToModel(UserPostResource userPostResource);

    @AfterMapping
    public void afterPostResourceToModel(UserPostResource userPostResource, @MappingTarget UserModel userModel) {
        Set<RoleModel> roles = userPostResource.getRoles().stream().map(roleModel -> roleDaoService.findOneBy(RoleSpecification.withRole(roleModel))).collect(Collectors.toSet());
        userModel.setRoles(roles);
    }

    public abstract UserModel patchResourceToModel(UserPatchResource userPatchResource, @MappingTarget UserModel existingUser);

    @AfterMapping
    public void afterPatchResourceToModel(UserPatchResource userPatchResource, @MappingTarget UserModel userModel) {
        if(!CollectionUtils.isEmpty(userPatchResource.getRoles())) {
            Set<RoleModel> roles = userPatchResource.getRoles().stream().map(roleModel -> roleDaoService.findOneBy(RoleSpecification.withRole(roleModel))).collect(Collectors.toSet());
            userModel.setRoles(roles);
        }
    }

}
