/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.controller;

import com.smsmode.booking.resource.fee.FeePostResource;
import com.smsmode.booking.resource.fee.FeePatchResource;
import com.smsmode.booking.resource.fee.get.FeeGetResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
@RequestMapping("fees")
public interface FeeController {

    @PostMapping
    ResponseEntity<FeeGetResource> postFee(@RequestBody FeePostResource feePostResource);

    @PatchMapping("{feeId}")
    ResponseEntity<FeeGetResource> patchFeeById(@PathVariable("feeId") String feeId,
                                                @RequestBody FeePatchResource feePatchResource);

    @DeleteMapping("{feeId}")
    ResponseEntity<Void> deleteFeeById(@PathVariable("feeId") String feeId);
}
