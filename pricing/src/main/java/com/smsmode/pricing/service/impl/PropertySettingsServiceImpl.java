/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.PropertySettingsDaoService;
import com.smsmode.pricing.mapper.PropertySettingsMapper;
import com.smsmode.pricing.model.PropertySettingsModel;
import com.smsmode.pricing.resource.settings.PropertySettingsGetResource;
import com.smsmode.pricing.resource.settings.PropertySettingsPostResource;
import com.smsmode.pricing.service.PropertySettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PropertySettingsServiceImpl implements PropertySettingsService {

    private final PropertySettingsDaoService propertySettingsDaoService;
    private final PropertySettingsMapper propertySettingsMapper;

    @Override
    public ResponseEntity<PropertySettingsGetResource> create(PropertySettingsPostResource propertySettingsPostResource) {
        PropertySettingsModel propertySettingsModel = new PropertySettingsModel();
        propertySettingsModel = propertySettingsMapper.postResourceToModel(propertySettingsPostResource, propertySettingsModel);
        propertySettingsModel = propertySettingsDaoService.save(propertySettingsModel);
        return ResponseEntity.ok(propertySettingsMapper.modelToGetResource(propertySettingsModel));
    }
}
