package com.smsmode.invoice.mapper;

import com.smsmode.invoice.model.PostingAccountModel;
import com.smsmode.invoice.model.base.AbstractBaseModel;
import com.smsmode.invoice.resource.common.AuditGetResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsGetResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPatchResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class PostingAccountsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountClass", ignore = true)
    @Mapping(target = "ledgerGroup", ignore = true)
    @Mapping(target = "subLedgerGroup", ignore = true)
    public abstract PostingAccountModel postResourceToModel(PostingAccountsPostResource postResource);

    public abstract PostingAccountsGetResource modelToGetResource(PostingAccountModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountClass", ignore = true)
    @Mapping(target = "ledgerGroup", ignore = true)
    @Mapping(target = "subLedgerGroup", ignore = true)
    public abstract PostingAccountModel patchResourceToModel(PostingAccountsPatchResource patchResource,
                                                             @MappingTarget PostingAccountModel model);

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @AfterMapping
    protected void afterModelToGetResource(PostingAccountModel model,
                                           @MappingTarget PostingAccountsGetResource resource) {
        resource.setAudit(this.modelToAuditResource(model));
    }
}
