/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.inventory.get.UnitInventoryGetResource;
import com.smsmode.unit.resource.unit.UnitGetResource;
import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.UnitItemGetResource;
import com.smsmode.unit.resource.unit.UnitPostResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UnitMapper {

    public abstract UnitItemGetResource modelToItemGetResource(UnitModel unitModel);

    @AfterMapping
    public void afterModelToItemGetResource(UnitModel unitModel, @MappingTarget UnitItemGetResource unitItemGetResource) {
        unitItemGetResource.setAudit(this.modelToAuditResource(unitModel));
        if (!ObjectUtils.isEmpty(unitItemGetResource.getParent())) {
            unitItemGetResource.setAddress(unitModel.getParent().getAddress());
            unitItemGetResource.setContact(unitModel.getParent().getContact());
        }
    }

    public abstract UnitGetResource modelToGetResource(UnitModel unitModel);

    @AfterMapping
    public void afterModelToGetResource(UnitModel unitModel, @MappingTarget UnitGetResource unitGetResource) {
        unitGetResource.setAudit(this.modelToAuditResource(unitModel));
        if (!ObjectUtils.isEmpty(unitGetResource.getParent())) {
            unitGetResource.setAddress(unitModel.getParent().getAddress());
            unitGetResource.setContact(unitModel.getParent().getContact());
        }
    }

    public abstract UnitModel postResourceToModel(UnitPostResource unitPostResource);


    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    public abstract UnitModel infosPatchResourceToModel(UnitInfosPatchResource unitInfosPatchResource, @MappingTarget UnitModel unit);

    public abstract UnitInfosGetResource modelToInfosGetResource(UnitModel unit);

    @AfterMapping
    public void afterModelToInfosGetResource(UnitModel unitModel, @MappingTarget UnitInfosGetResource unitInfosGetResource) {
        unitInfosGetResource.setAudit(this.modelToAuditResource(unitModel));
        if (!ObjectUtils.isEmpty(unitInfosGetResource.getParent())) {
            unitInfosGetResource.setAddress(unitModel.getParent().getAddress());
            unitInfosGetResource.setContact(unitModel.getParent().getContact());
        }
    }

    public abstract UnitDetailsGetResource modelToDetailsGetResource(UnitModel unit);

    public abstract UnitModel detailsPatchResourceToModel(UnitDetailsPatchResource unitDetailsPatchResource, @MappingTarget UnitModel unit);

    public abstract UnitInventoryGetResource modelToInventoryGetResource(UnitModel unitModel);
}
