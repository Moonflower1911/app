/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.controller;

import com.smsmode.pricing.resource.settings.PropertySettingsGetResource;
import com.smsmode.pricing.resource.settings.PropertySettingsPostResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Sep 2025</p>
 */
@RequestMapping("property-settings")
public interface PropertySettingsController {

    @PostMapping
    ResponseEntity<PropertySettingsGetResource> post(@RequestBody PropertySettingsPostResource propertySettingsPostResource);
}
