/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.details;

import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.enumeration.AmenityEnum;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.resource.unit.ParentUnitGetResource;
import com.smsmode.unit.resource.unit.UnitGetResource;
import lombok.Data;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Jun 2025</p>
 */
@Data
public class UnitDetailsGetResource {
    private String id;
    private String name;
    private UnitTypeEnum type;
    private Double floorSize;
    private FloorSizeUnitEnum floorSizeUnit;
    private String description;
    private OccupancyEmbeddable minOccupancy;
    private OccupancyEmbeddable maxOccupancy;
    private Integer travellerAge;
    private boolean childrenAllowed;
    private boolean eventsAllowed;
    private boolean smokingAllowed;
    private boolean petsAllowed;
    private Set<AmenityEnum> amenities;
    private ParentUnitGetResource parent;
    private UnitNatureEnum nature;
}
