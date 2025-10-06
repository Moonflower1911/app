package com.smsmode.task.mapper;

import com.smsmode.task.model.IncidentModel;
import com.smsmode.task.model.base.AbstractBaseModel;
import com.smsmode.task.resource.common.AuditGetResource;
import com.smsmode.task.resource.incident.IncidentItemGetResource;
import com.smsmode.task.resource.incident.IncidentPostResource;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class IncidentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract IncidentModel postResourceToModel(IncidentPostResource incidentPostResource);


    public abstract IncidentItemGetResource modelToItemGetResource(IncidentModel incidentModel);

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @AfterMapping
    public void afterModelToItemGetResource(IncidentModel incidentModel, @MappingTarget IncidentItemGetResource incidentItemGetResource) {
        incidentItemGetResource.setAudit(this.modelToAuditResource(incidentModel));
    }
}
