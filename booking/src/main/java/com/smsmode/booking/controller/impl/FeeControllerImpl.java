/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.controller.impl;

import com.smsmode.booking.controller.FeeController;
import com.smsmode.booking.resource.fee.FeePatchResource;
import com.smsmode.booking.resource.fee.FeePostResource;
import com.smsmode.booking.resource.fee.get.FeeGetResource;
import com.smsmode.booking.service.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class FeeControllerImpl implements FeeController {

    private final FeeService feeService;

    @Override
    public ResponseEntity<FeeGetResource> postFee(FeePostResource feePostResource) {
        return feeService.createFee(feePostResource);
    }

    @Override
    public ResponseEntity<FeeGetResource> patchFeeById(String feeId, FeePatchResource feePatchResource) {
        return feeService.updateFeeById(feeId, feePatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteFeeById(String feeId) {
        return feeService.removeById(feeId);
    }
}
