/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.details;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.RoomSubTypeEnum;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Jun 2025</p>
 */
@Data
public class RoomGetResource {
    private String id;
    private String name;
    private RoomTypeEnum type;
    private Integer floorSize;
    private FloorSizeUnitEnum floorSizeUnitEnum;
    private RoomSubTypeEnum subType;
    private RoomGetResource bathroom;
    private List<BedEmbeddable> beds;
}
