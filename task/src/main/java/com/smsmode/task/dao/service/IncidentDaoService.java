package com.smsmode.task.dao.service;

import com.smsmode.task.model.IncidentModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncidentDaoService {
    IncidentModel save(IncidentModel incidentModel);

    Page<IncidentModel> findAllBy(Specification<IncidentModel> specification, Pageable pageable);

    IncidentModel findOneBy(Specification<IncidentModel> specification);

    void deleteBy(Specification<IncidentModel> specification);

}
