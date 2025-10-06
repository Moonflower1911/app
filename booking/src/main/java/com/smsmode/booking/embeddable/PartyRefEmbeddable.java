/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.embeddable;

import jakarta.persistence.Column;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Aug 2025</p>
 */
@Data
public class PartyRefEmbeddable {
    @Column(name = "party_id")
    private String id;
    @Column(name = "party_name")
    private String name;
}
