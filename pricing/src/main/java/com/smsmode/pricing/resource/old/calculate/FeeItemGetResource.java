/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.resource.old.calculate;

import com.smsmode.pricing.embeddable.AgeBucketEmbeddable;
import com.smsmode.pricing.enumeration.GuestTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Aug 2025</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeItemGetResource {
    private GuestTypeEnum guestType;
    private AgeBucketEmbeddable ageBucket;
    private Integer quantity;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeeItemGetResource that)) return false;
        return guestType == that.guestType &&
               Objects.equals(ageBucket, that.ageBucket) &&
               Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guestType, ageBucket, price);
    }
}
