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

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Apr 2025</p>
 */
@Data
public class UnitItemGetResource {
    private String id;
    private String name;
    private String code;
    private String subTitle;
    private int beds;
    private int bathrooms;
    private AuditGetResource audit;
    private ContactEmbeddable contact;
    private AddressEmbeddable address;
    private OccupancyGetResource occupancy;
    private boolean readiness;
    private UnitNatureEnum nature;
    private List<UnitItemGetResource> subUnits;
    private ParentUnitGetResource parent;
    private int priority;
}
