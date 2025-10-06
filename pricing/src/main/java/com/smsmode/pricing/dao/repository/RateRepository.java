/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.repository;

import com.smsmode.pricing.model.RateModel;
import com.smsmode.pricing.model.UnitRefModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Repository
public interface RateRepository extends JpaRepository<RateModel, String>, JpaSpecificationExecutor<RateModel> {

    @Query(value = """
           SELECT * 
           FROM n_rate r
           WHERE r.rate_plan_id = :ratePlanId
             AND r.unit_id IN (:unitIds)
             AND r.rate_date BETWEEN :startDate AND :endDate
           """, nativeQuery = true)
    List<RateModel> findRates(@Param("ratePlanId") String ratePlanId,
                              @Param("unitIds") List<String> unitIds,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);

}
