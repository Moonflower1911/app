/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.dao.repository;

import com.smsmode.guest.model.SegmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
@Repository
public interface SegmentRepository extends JpaRepository<SegmentModel, String>, JpaSpecificationExecutor<SegmentModel> {

    @Modifying
    @Query("UPDATE SegmentModel s SET s.enabled = false WHERE s.parent.id = :parentId")
    void disableSegmentsByParentId(@Param("parentId") String parentId);
}
