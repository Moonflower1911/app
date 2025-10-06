package com.smsmode.invoice.mapper;

import com.smsmode.invoice.model.LedgerGroupModel;
import com.smsmode.invoice.model.base.AbstractBaseModel;
import com.smsmode.invoice.resource.common.AuditGetResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupGetResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupParentResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPatchResource;
import com.smsmode.invoice.resource.ledgergroup.LedgerGroupPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class LedgerGroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    public abstract LedgerGroupModel postResourceToModel(LedgerGroupPostResource postResource);

    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "subledgerCount", ignore = true)
    public abstract LedgerGroupGetResource modelToGetResource(LedgerGroupModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    public abstract LedgerGroupModel patchResourceToModel(LedgerGroupPatchResource patchResource,
                                                          @MappingTarget LedgerGroupModel model);

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @Named("toParentResource")
    LedgerGroupParentResource toParentResource(LedgerGroupModel parent) {
        if (parent == null) {
            return null;
        }
        LedgerGroupParentResource resource = new LedgerGroupParentResource();
        resource.setId(parent.getId());
        resource.setName(parent.getName());
        return resource;
    }

    @AfterMapping
    protected void afterModelToGetResource(LedgerGroupModel model, @MappingTarget LedgerGroupGetResource resource) {
        resource.setAudit(this.modelToAuditResource(model));
        if (model.getParent() != null) {
            resource.setParent(toParentResource(model.getParent()));
        }
    }
}
