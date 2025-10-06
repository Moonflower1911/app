/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.UnitRoomController;
import com.smsmode.unit.resource.unit.rooms.RoomGetResource;
import com.smsmode.unit.resource.unit.rooms.RoomPatchResource;
import com.smsmode.unit.resource.unit.rooms.RoomPostResource;
import com.smsmode.unit.resource.unit.rooms.UnitRoomsGetResource;
import com.smsmode.unit.service.UnitRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jun 2025</p>
 */
@Slf4j
@Service
@RestController
@RequiredArgsConstructor
public class UnitRoomControllerImpl implements UnitRoomController {

    private final UnitRoomService unitRoomService;

    @Override
    public ResponseEntity<Page<RoomGetResource>> getUnitRooms(String unitId, Pageable pageable) {
        return unitRoomService.retrieveUnitRooms(unitId, pageable);
    }

    @Override
    public ResponseEntity<RoomGetResource> postUnitRoom(String unitId, RoomPostResource roomPostResource) {
        return unitRoomService.createUnitRoom(unitId, roomPostResource);
    }

    @Override
    public ResponseEntity<RoomGetResource> patchUnitById(String unitId, String roomId, RoomPatchResource roomPatchResource) {
        return unitRoomService.updateUnitRooms(unitId, roomId, roomPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteUnitRoomById(String unitId, String roomId) {
        return unitRoomService.deleteUnitRoomById(unitId, roomId);
    }
}
