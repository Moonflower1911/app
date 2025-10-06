/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.PropertySettingsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Sep 2025</p>
 */
public interface PropertySettingsDaoService {
    PropertySettingsModel save(PropertySettingsModel propertySettingsModel);

    PropertySettingsModel findOneBy(Specification<PropertySettingsModel> specification);

    Page<PropertySettingsModel> findAllBy(Specification<PropertySettingsModel> specification, Pageable pageable);
}
