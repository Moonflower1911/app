/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.controller;

import com.smsmode.booking.resource.kpis.TotalCountGetResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 13 Aug 2025</p>
 */
@RequestMapping("kpis")
public interface KpiController {

     @GetMapping("checkins")
     ResponseEntity<TotalCountGetResource> getCheckins();

     @GetMapping("checkouts")
     ResponseEntity<TotalCountGetResource> getCheckouts();

     @GetMapping("in-house")
     ResponseEntity<TotalCountGetResource> getInHouse();

     @GetMapping("booking")
     ResponseEntity<TotalCountGetResource> getBooking();
}
