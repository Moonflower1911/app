/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.projection;

import com.smsmode.unit.embeddable.BedEmbeddable;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 29 Jul 2025</p>
 */
public interface FlatUnitBedProjection {
    String getUnitId();
    BedEmbeddable getBed();
}
