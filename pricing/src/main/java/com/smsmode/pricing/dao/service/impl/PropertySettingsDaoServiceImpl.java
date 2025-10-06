/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.PropertySettingsRepository;
import com.smsmode.pricing.dao.service.PropertySettingsDaoService;
import com.smsmode.pricing.exception.ResourceNotFoundException;
import com.smsmode.pricing.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.pricing.model.PropertySettingsModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PropertySettingsDaoServiceImpl implements PropertySettingsDaoService {
    private final PropertySettingsRepository propertySettingsRepository;

    @Override
    public PropertySettingsModel save(PropertySettingsModel propertySettingsModel) {
        return propertySettingsRepository.save(propertySettingsModel);
    }

    @Override
    public PropertySettingsModel findOneBy(Specification<PropertySettingsModel> specification) {
        return propertySettingsRepository.findOne(specification).orElseThrow(() -> {
            log.warn("No property settings found corresponding to specification. Will throw an error ...");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.PROPERTY_SETTINGS_NOT_FOUND,
                    "No property settings found based on your criteria");
        });
    }

    @Override
    public Page<PropertySettingsModel> findAllBy(Specification<PropertySettingsModel> specification, Pageable pageable) {
        return propertySettingsRepository.findAll(specification, pageable);
    }
}
