/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.old.calculate;

import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 12 Aug 2025</p>
 */
@Data
public class UnitFeePostResource {
    private Integer nights;
    private GuestsPostResource guests;
    private String feeId;
}
