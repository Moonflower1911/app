/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.repository;

import com.smsmode.unit.dao.projection.FlatUnitBedProjection;
import com.smsmode.unit.dao.projection.UnitSubCountProjection;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.model.UnitModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Repository
public interface UnitRepository extends JpaRepository<UnitModel, String>, JpaSpecificationExecutor<UnitModel> {
    @Modifying
    @Query("UPDATE UnitModel u SET u.type = :type WHERE u.parent.id = :parentUnitId")
    void updateTypeByParentUnitId(@Param("parentUnitId") String parentUnitId, @Param("type") UnitTypeEnum type);

    @Query("SELECT u.id FROM UnitModel u")
    List<String> findAllUnitIds();


    @Query(
            value = """
                    SELECT * FROM x_unit u
                    WHERE (
                        u.nature = 'SINGLE'
                        AND u.readiness = true
                        AND u.parent_id IS NULL
                        AND u.id NOT IN (:reservedIds)
                    )
                    OR (
                        u.nature = 'MULTI_UNIT'
                        AND u.readiness = true
                        AND ( SELECT COUNT(*) FROM x_unit su
                            WHERE su.parent_id = u.id 
                            AND su.readiness = true ) > (
                            SELECT COUNT(*) FROM (
                                SELECT unnest(CAST(:reservedIds AS varchar[])) AS booked_parent_id
                            ) AS booking_count
                            WHERE booking_count.booked_parent_id = u.id
                        )
                    )
                    """,
            nativeQuery = true
    )
    Page<UnitModel> findAvailableUnits(@Param("reservedIds") String[] reservedIds, Pageable pageable);

    @Query("""
            SELECT u.id AS unitId, COUNT(su.id) AS subUnitCount
            FROM UnitModel su
            JOIN su.parent u
            WHERE u.id IN :unitIds
            AND su.readiness = true
            GROUP BY u.id
            """)
    List<UnitSubCountProjection> countSubUnitsFor(@Param("unitIds") List<String> unitIds);

    @Query("""
            SELECT r.unit.id AS unitId, b AS bed
            FROM RoomModel r JOIN r.beds b
            WHERE r.unit.id IN :unitIds
            AND r.type IN :roomTypes
            """)
    List<FlatUnitBedProjection> findUnitBeds(
            @Param("unitIds") List<String> unitIds,
            @Param("roomTypes") List<RoomTypeEnum> roomTypes
    );
}
