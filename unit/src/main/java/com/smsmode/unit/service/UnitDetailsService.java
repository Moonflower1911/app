/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Jun 2025</p>
 */
public interface UnitDetailsService {
     ResponseEntity<UnitDetailsGetResource> retrieveDetailsById(String unitId);

     ResponseEntity<UnitDetailsGetResource> update(String unitId, UnitDetailsPatchResource unitDetailsPatchResource);

}
