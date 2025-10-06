/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 May 2025</p>
 */
@Data
public class UnitInfosPatchResource {
    private String name;
    private String subtitle;
    private ContactEmbeddable contact;
    private AddressEmbeddable address;
    private Boolean readiness;
    private String calendarColor;
    private int priority;
}
