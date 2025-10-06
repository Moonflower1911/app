package com.smsmode.uaa.mappers;

import com.smsmode.uaa.model.AccountModel;
import com.smsmode.uaa.model.base.AbstractIntBaseModel;
import com.smsmode.uaa.resource.account.AccountItemGetResource;
import com.smsmode.uaa.resource.account.AccountPatchResource;
import com.smsmode.uaa.resource.account.AccountPostResource;
import com.smsmode.uaa.resource.common.AuditGetResource;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class AccountMapper {

    @Mapping(target = "audit", ignore = true)
    public abstract AccountItemGetResource modelToGetResource(AccountModel accountModel);

    @AfterMapping
    public void afterMapping(AccountModel accountModel, @MappingTarget AccountItemGetResource resource) {
        resource.setAudit(this.modelToAuditResource(accountModel));
    }

    public abstract AuditGetResource modelToAuditResource(AbstractIntBaseModel baseModel);

    public abstract AccountModel postResourceToModel(AccountPostResource accountPostResource);

    public abstract AccountModel patchResourceToModel(AccountPatchResource accountPatchResource,
                                                      @MappingTarget AccountModel existingAccount);
}
