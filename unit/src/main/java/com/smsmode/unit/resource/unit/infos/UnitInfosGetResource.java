/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.infos;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.unit.ParentUnitGetResource;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 May 2025</p>
 */
@Data
public class UnitInfosGetResource {
    private String id;
    private String name;
    private String subtitle;
    private UnitNatureEnum nature;
    private ContactEmbeddable contact;
    private AddressEmbeddable address;
    private boolean readiness;
    private String calendarColor;
    private ParentUnitGetResource parent;
    private AuditGetResource audit;
}
