/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.settings;

import lombok.Data;

import java.time.LocalTime;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 26 Sep 2025</p>
 */
@Data
public class PropertySettingsPostResource {
    private Integer childMinAge;
    private Integer childMaxAge;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
}
