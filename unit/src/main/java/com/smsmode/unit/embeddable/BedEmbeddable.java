/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.embeddable;

import com.smsmode.unit.enumeration.BedTypeEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jun 2025</p>
 */
@Getter
@Setter
@Embeddable
public class BedEmbeddable {
    @Enumerated(EnumType.STRING)
    private BedTypeEnum type;
    private int quantity;
}
