package com.smsmode.task.controller.impl;

import com.smsmode.task.controller.IncidentController;
import com.smsmode.task.resource.incident.IncidentItemGetResource;
import com.smsmode.task.resource.incident.IncidentPatchResource;
import com.smsmode.task.resource.incident.IncidentPostResource;
import com.smsmode.task.service.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class IncidentControllerImpl implements IncidentController {

    private final IncidentService incidentService;

    @Override
    public ResponseEntity<IncidentItemGetResource> createIncident(IncidentPostResource incidentPostResource, MultipartFile[] incidentImages) {
        return incidentService.create(incidentPostResource, incidentImages);
    }

    @Override
    public ResponseEntity<Page<IncidentItemGetResource>> getAllIncidents(String search, Pageable pageable){
        return incidentService.retrieveAllByPage(search, pageable);
    }

    @Override
    public ResponseEntity<IncidentItemGetResource> getIncidentById(String incidentId){
        return incidentService.retrieveById(incidentId);
    }

    @Override
    public ResponseEntity<IncidentItemGetResource> updateIncident(String incidentId, IncidentPatchResource incidentPatchResource){
        return incidentService.updateById(incidentId, incidentPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteIncident(String incidentId) {
        return incidentService.deleteById(incidentId);
    }

}
