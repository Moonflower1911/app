package com.smsmode.task.dao.service.Impl;

import com.smsmode.task.dao.service.IncidentDaoService;
import com.smsmode.task.dao.repository.IncidentRepository;
import com.smsmode.task.exception.ResourceNotFoundException;
import com.smsmode.task.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.task.model.IncidentModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentDaoServiceImpl implements IncidentDaoService {

    private final IncidentRepository incidentRepository;

    @Override
    public IncidentModel save(IncidentModel incidentModel) {
        return incidentRepository.save(incidentModel);
    }

    @Override
    public Page<IncidentModel> findAllBy(Specification<IncidentModel> specification, Pageable pageable){
        return incidentRepository.findAll(specification, pageable);
    }

    @Override
    public IncidentModel findOneBy(Specification<IncidentModel> specification) {
        return incidentRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any incident with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.INCIDENT_NOT_FOUND,
                            "No incident found with the specified criteria");
                });
    }

    @Override
    public void deleteBy(Specification<IncidentModel> specification) {
        incidentRepository.deleteAll(incidentRepository.findAll(specification));
    }
}
