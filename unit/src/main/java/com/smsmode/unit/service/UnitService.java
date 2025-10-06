/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.unit.SubUnitListResource;
import com.smsmode.unit.resource.unit.UnitGetResource;
import com.smsmode.unit.resource.unit.UnitItemGetResource;
import com.smsmode.unit.resource.unit.UnitPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
public interface UnitService {

    ResponseEntity<UnitGetResource> create(UnitPostResource unitPostResource);

    ResponseEntity<Page<UnitItemGetResource>> retrieveAllByPage(String search, UnitNatureEnum nature, Boolean withParent, Pageable pageable);

    ResponseEntity<UnitGetResource> retrieveById(String unitId);

    ResponseEntity<UnitItemGetResource> addSubUnitToMultiUnit(String parentUnitId, SubUnitListResource subUnitListResource);

    void detachSubUnit(String subUnitId);

    ResponseEntity<Page<UnitItemGetResource>> getSubUnitsOfMultiUnit(String parentUnitId, String search, Pageable pageable);

}
