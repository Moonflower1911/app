/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.service;

import com.smsmode.booking.resource.kpis.TotalCountGetResource;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 13 Aug 2025</p>
 */
public interface KpiService {

    ResponseEntity<TotalCountGetResource> calculateCheckins();
    ResponseEntity<TotalCountGetResource> calculateCheckouts();
    ResponseEntity<TotalCountGetResource> calculateInHouse();
    ResponseEntity<TotalCountGetResource> calculateBooking();
}
