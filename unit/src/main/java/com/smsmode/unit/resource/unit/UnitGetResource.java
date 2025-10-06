/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.common.AuditGetResource;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 20 Apr 2025</p>
 */
@Data
public class UnitGetResource {
    private String id;
    private String name;
    private String code;
    private String subTitle;
    private AuditGetResource audit;
    private ContactEmbeddable contact;
    private AddressEmbeddable address;
    private boolean readiness;
    private ParentUnitGetResource parent;
    private UnitNatureEnum nature;
}
