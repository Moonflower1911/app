/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.rooms.RoomGetResource;
import com.smsmode.unit.resource.unit.rooms.RoomPostResource;
import com.smsmode.unit.resource.unit.rooms.RoomPatchResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jun 2025</p>
 */
public interface UnitRoomService {


    ResponseEntity<Page<RoomGetResource>> retrieveUnitRooms(String unitId, Pageable pageable);

    ResponseEntity<RoomGetResource> createUnitRoom(String unitId, RoomPostResource roomPostResource);

    ResponseEntity<RoomGetResource> updateUnitRooms(String unitId, String roomId, RoomPatchResource roomPatchResource);

    ResponseEntity<Void> deleteUnitRoomById(String unitId, String roomId);


}
