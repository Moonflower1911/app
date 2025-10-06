package com.smsmode.task.service;

import com.smsmode.task.resource.incident.IncidentItemGetResource;
import com.smsmode.task.resource.incident.IncidentPatchResource;
import com.smsmode.task.resource.incident.IncidentPostResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncidentService {
    ResponseEntity<IncidentItemGetResource> create(IncidentPostResource incidentPostResource, MultipartFile[] incidentImages);

    ResponseEntity<Page<IncidentItemGetResource>> retrieveAllByPage(String search,Pageable pageable);

    ResponseEntity<IncidentItemGetResource> retrieveById(String incidentId);

    ResponseEntity<IncidentItemGetResource> updateById(String incidentId, IncidentPatchResource incidentPatchResource);

    ResponseEntity<Void> deleteById(String incidentId);
}
