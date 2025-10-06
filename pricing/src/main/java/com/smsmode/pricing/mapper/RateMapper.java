/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.mapper;

import com.smsmode.pricing.model.RateModel;
import com.smsmode.pricing.resource.rate.DailyRateGetResource;
import com.smsmode.pricing.resource.rate.RatePostResource;
import org.mapstruct.*;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 05 Sep 2025</p>
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RateMapper {

    @Mapping(source = "date", target = "date")
    public abstract DailyRateGetResource modelToDailyRateGetResource(RateModel rateModel);

    public abstract RateModel postResourceToModel(RatePostResource ratePostResource);

    @Mapping(target = "singleAdult", ignore = true)
    @Mapping(target = "doubleAdult", ignore = true)
    @Mapping(target = "extraAdult", ignore = true)
    @Mapping(target = "extraChild", ignore = true)
    @Mapping(target = "date", ignore = true)
    public abstract DailyRateGetResource modelToGetResource(RateModel rateModel, @Context LocalDate date);

    @AfterMapping
    public void afterModelToGetResource(RateModel rateModel, @Context LocalDate date, @MappingTarget DailyRateGetResource dailyRateGetResource) {
        if (ObjectUtils.isEmpty(rateModel)) {
            dailyRateGetResource.setDate(date);
            dailyRateGetResource.setSingleAdult(BigDecimal.ZERO);
            dailyRateGetResource.setDoubleAdult(BigDecimal.ZERO);
            dailyRateGetResource.setExtraAdult(BigDecimal.ZERO);
            dailyRateGetResource.setExtraChild(BigDecimal.ZERO);
        } else {
            dailyRateGetResource.setDate(rateModel.getDate());
            dailyRateGetResource.setSingleAdult(ObjectUtils.isEmpty(rateModel.getSingleAdult()) ? BigDecimal.ZERO : rateModel.getSingleAdult());
            dailyRateGetResource.setDoubleAdult(ObjectUtils.isEmpty(rateModel.getDoubleAdult()) ? BigDecimal.ZERO : rateModel.getDoubleAdult());
            dailyRateGetResource.setExtraAdult(ObjectUtils.isEmpty(rateModel.getExtraAdult()) ? BigDecimal.ZERO : rateModel.getExtraAdult());
            dailyRateGetResource.setExtraChild(ObjectUtils.isEmpty(rateModel.getExtraChild()) ? BigDecimal.ZERO : rateModel.getExtraChild());
        }


    }
}
