package com.smsmode.pricing.dao.old.repository;

import com.smsmode.pricing.model.old.FeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<FeeModel, String>, JpaSpecificationExecutor<FeeModel> {
}
