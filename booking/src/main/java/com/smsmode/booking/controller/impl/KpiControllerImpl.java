/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.controller.impl;

import com.smsmode.booking.controller.KpiController;
import com.smsmode.booking.resource.kpis.TotalCountGetResource;
import com.smsmode.booking.service.KpiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 13 Aug 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class KpiControllerImpl implements KpiController {

    private final KpiService kpiService;

    @Override
    public ResponseEntity<TotalCountGetResource> getCheckins() {
        return kpiService.calculateCheckins();
    }

    @Override
    public ResponseEntity<TotalCountGetResource> getCheckouts() {
        return kpiService.calculateCheckouts();
    }

    @Override
    public ResponseEntity<TotalCountGetResource> getInHouse() {
        return   kpiService.calculateInHouse();
    }

    @Override
    public ResponseEntity<TotalCountGetResource> getBooking() {
        return   kpiService.calculateBooking();
    }
}
