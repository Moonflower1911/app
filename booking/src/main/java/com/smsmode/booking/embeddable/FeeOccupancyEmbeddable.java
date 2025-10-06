/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.embeddable;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
@Data
@Embeddable
public class FeeOccupancyEmbeddable {
    private Integer adults;
    @Embedded
    private FeeChildOccupancyEmbeddable children;
}
