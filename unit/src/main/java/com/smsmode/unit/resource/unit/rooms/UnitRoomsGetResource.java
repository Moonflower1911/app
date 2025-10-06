/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rooms;

import com.smsmode.unit.resource.unit.details.RoomGetResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jun 2025</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRoomsGetResource {
    private List<RoomGetResource> rooms;
}
