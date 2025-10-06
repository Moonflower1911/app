/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.dao.repository;

import com.smsmode.booking.model.FeeModel;
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
 * <p>Created 08 Aug 2025</p>
 */
@Repository
public interface FeeRepository extends JpaRepository<FeeModel, String>, JpaSpecificationExecutor<FeeModel> {

    @Modifying
    @Query("""
                DELETE FROM FeeModel f
                WHERE f.booking.id = :bookingId
            """)
    void deleteByBookingId(@Param("bookingId") String bookingId);

}
