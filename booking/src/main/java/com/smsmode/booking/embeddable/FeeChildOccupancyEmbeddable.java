/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Aug 2025</p>
 */
@Data
@Embeddable
public class FeeChildOccupancyEmbeddable {
    @Embedded
    private AgeBucketEmbeddable ageBucket;
    @Column(name = "children_quantity")
    private Integer quantity;

}
