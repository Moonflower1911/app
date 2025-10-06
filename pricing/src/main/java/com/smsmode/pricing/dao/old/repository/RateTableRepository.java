package com.smsmode.pricing.dao.old.repository;

import com.smsmode.pricing.model.old.RateTableModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RateTable entity operations.
 */
@Repository
public interface RateTableRepository extends JpaRepository<RateTableModel, String>, JpaSpecificationExecutor<RateTableModel> {
}