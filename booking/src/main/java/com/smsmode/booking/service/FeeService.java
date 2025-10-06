/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.service;

import com.smsmode.booking.resource.fee.FeePatchResource;
import com.smsmode.booking.resource.fee.FeePostResource;
import com.smsmode.booking.resource.fee.get.FeeGetResource;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
public interface FeeService {

    ResponseEntity<FeeGetResource> createFee(FeePostResource feePostResource);

    ResponseEntity<FeeGetResource> updateFeeById(String feeId, FeePatchResource feePatchResource);

    ResponseEntity<Void> removeById(String feeId);


}
