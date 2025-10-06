/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.inventory.get;

import lombok.Data;
import lombok.ToString;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 29 Jul 2025</p>
 */
@Data
@ToString
public class AvailabilityGetResource {
    private Long quantity;
    private Long available;
}
