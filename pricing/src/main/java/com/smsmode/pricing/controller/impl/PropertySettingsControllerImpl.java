/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller.impl;

import com.smsmode.pricing.controller.PropertySettingsController;
import com.smsmode.pricing.resource.settings.PropertySettingsGetResource;
import com.smsmode.pricing.resource.settings.PropertySettingsPostResource;
import com.smsmode.pricing.service.PropertySettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Sep 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PropertySettingsControllerImpl implements PropertySettingsController {

    private final PropertySettingsService propertySettingsService;

    @Override
    public ResponseEntity<PropertySettingsGetResource> post(PropertySettingsPostResource propertySettingsPostResource) {
        return propertySettingsService.create(propertySettingsPostResource);
    }
}
