/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.rateplan;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Sep 2025</p>
 */
@Getter
@Setter
public class RestrictionsPatchResource {
    private Integer minLos;
    private Integer maxLos;
    private Integer minLead;
    private Integer maxLead;
}
