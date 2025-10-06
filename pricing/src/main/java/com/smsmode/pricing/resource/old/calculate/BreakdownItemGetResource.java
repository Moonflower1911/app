/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.old.calculate;

import com.smsmode.pricing.embeddable.AgeBucketEmbeddable;
import com.smsmode.pricing.enumeration.GuestTypeEnum;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Aug 2025</p>
 */
@Data
public class BreakdownItemGetResource {
    private GuestTypeEnum guestType;
    private AgeBucketEmbeddable ageBucket;
    private Integer quantity;
    private RateGetResource rate;
}
