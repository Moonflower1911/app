package com.smsmode.task.dao.repository;

import com.smsmode.task.model.IncidentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentModel, String>, JpaSpecificationExecutor<IncidentModel> {
}
