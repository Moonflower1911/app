/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.unit.rooms.RoomGetResource;
import com.smsmode.unit.resource.unit.rooms.RoomPostResource;
import com.smsmode.unit.resource.unit.rooms.RoomPatchResource;
import com.smsmode.unit.resource.unit.rooms.UnitRoomsGetResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jun 2025</p>
 */
@RequestMapping("/units/{unitId}/rooms")
public interface UnitRoomController {

    @GetMapping
    ResponseEntity<Page<RoomGetResource>> getUnitRooms(@PathVariable("unitId") String unitId, Pageable pageable);

    @PostMapping
    ResponseEntity<RoomGetResource> postUnitRoom(@PathVariable("unitId") String unitId, @RequestBody RoomPostResource roomPostResource);

    @PatchMapping("/{roomId}")
    ResponseEntity<RoomGetResource> patchUnitById(@PathVariable("unitId") String unitId, @PathVariable("roomId") String roomId, @RequestBody RoomPatchResource roomPatchResource);

    @DeleteMapping("/{roomId}")
    ResponseEntity<Void> deleteUnitRoomById(@PathVariable("unitId") String unitId, @PathVariable("roomId") String roomId);
}
