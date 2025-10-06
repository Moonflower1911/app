/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.invoice.mapper;

import com.smsmode.invoice.model.VatRuleModel;
import com.smsmode.invoice.resource.vatrule.VatRuleGetResource;
import com.smsmode.invoice.resource.vatrule.VatRulePatchResource;
import com.smsmode.invoice.resource.vatrule.VatRulePostResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class VatRuleMapper {
    public abstract VatRuleGetResource modelToGetResource(VatRuleModel vatRuleModel);

    public abstract VatRuleModel postResourceToModel(VatRulePostResource vatRulePostResource);

    @Mapping(target = "id", ignore = true)
    public abstract VatRuleModel patchResourceToModel(VatRulePatchResource vatRulePatchResource, @MappingTarget VatRuleModel vatRuleModel);
}
