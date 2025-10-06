/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service;

import com.smsmode.unit.dao.projection.FlatUnitBedProjection;
import com.smsmode.unit.dao.projection.UnitSubCountProjection;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.model.UnitModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
public interface UnitDaoService {
    UnitModel save(UnitModel unitModel);

    Page<UnitModel> findAllBy(Specification<UnitModel> specification, Pageable pageable);

    UnitModel findOneBy(Specification<UnitModel> specification);

    UnitModel findById(String unitId);

    List<UnitModel> findByParentUnit(UnitModel parent);

    void updateTypeByParentUnitId(String id, UnitTypeEnum type);

    void saveAll(List<UnitModel> subUnits);

    Page<UnitModel> findAvailableUnits(String[] reservedUnitIdsArray, Pageable pageable);

    List<UnitSubCountProjection> countSubUnitsForMultiUnits(List<String> multiUnitIds);

    List<FlatUnitBedProjection> findUnitBeds(List<String> unitIds, List<RoomTypeEnum> roomTypes);

}
