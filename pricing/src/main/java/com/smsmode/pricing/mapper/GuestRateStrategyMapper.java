/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.GuestRateRuleModel;
import com.smsmode.pricing.model.GuestRateStrategyModel;
import com.smsmode.pricing.resource.guestratestrategy.*;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 05 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class GuestRateStrategyMapper {

    public abstract GuestRateStrategyItemGetResource modelToItemGetResource(GuestRateStrategyModel guestRateStrategyModel);

    @Mapping(target = "rules", source = "rules", qualifiedByName = "mapStrategyRules")
    public abstract GuestRateStrategyModel postResourceToModel(GuestRateStrategyPostResource guestRateStrategyPostResource);

    @Named("mapStrategyRules")
    public List<GuestRateRuleModel> mapStrategyRules(List<GuestRateRulePostResource> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return new ArrayList<>();
        }
        List<GuestRateRuleModel> result = new ArrayList<>();
        for (GuestRateRulePostResource resource : rules) {
            result.add(rulePostResourceToModel(resource));
        }
        return result;
    }

    public abstract GuestRateStrategyGetResource modelToGetResource(GuestRateStrategyModel guestRateStrategyModel);

    public abstract GuestRateRuleModel rulePostResourceToModel(GuestRateRulePostResource guestRateRulePostResource);

    @Mapping(target = "rules", ignore = true)
    public abstract GuestRateStrategyModel patchResourceToModel(GuestRateStrategyPatchResource guestRateStrategyPatchResource, @MappingTarget GuestRateStrategyModel guestRateStrategyModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "strategy", ignore = true)
    public abstract void updateGuestRateRuleFromResource(GuestRateRulePostResource priceResource, @MappingTarget GuestRateRuleModel existingPrice);
}
