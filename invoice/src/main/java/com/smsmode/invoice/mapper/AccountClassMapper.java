/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.smsmode.invoice.mapper;

import com.smsmode.invoice.model.AccountClassModel;
import com.smsmode.invoice.model.base.AbstractBaseModel;
import com.smsmode.invoice.resource.accountclass.AccountClassGetResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPatchResource;
import com.smsmode.invoice.resource.accountclass.AccountClassPostResource;
import com.smsmode.invoice.resource.common.AuditGetResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class AccountClassMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "systemCritical", ignore = true)
    @Mapping(target = "enabled", expression = "java(postResource.getEnabled() != null ? postResource.getEnabled() : true)")
    @Mapping(target = "description", source = "description")
    public abstract AccountClassModel postResourceToModel(AccountClassPostResource postResource);

    @Mapping(target = "description", source = "description")
    @Mapping(target = "enabled", source = "enabled")
    public abstract AccountClassGetResource modelToGetResource(AccountClassModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "systemCritical", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "enabled", source = "enabled")
    public abstract AccountClassModel patchResourceToModel(AccountClassPatchResource patchResource,
                                                           @MappingTarget AccountClassModel model);

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @AfterMapping
    protected void afterModelToGetResource(AccountClassModel model, @MappingTarget AccountClassGetResource resource) {
        resource.setAudit(this.modelToAuditResource(model));
    }
}
