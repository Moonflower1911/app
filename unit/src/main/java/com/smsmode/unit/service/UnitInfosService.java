/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 May 2025</p>
 */
public interface UnitInfosService {

    ResponseEntity<UnitInfosGetResource> update(String unitId, UnitInfosPatchResource unitInfosPatchResource);

    ResponseEntity<UnitInfosGetResource> retrieveById(String unitId);

}
