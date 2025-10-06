/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RoomDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.RoomSpecification;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.mapper.RoomMapper;
import com.smsmode.unit.model.RoomModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.rooms.RoomGetResource;
import com.smsmode.unit.resource.unit.rooms.RoomPatchResource;
import com.smsmode.unit.resource.unit.rooms.RoomPostResource;
import com.smsmode.unit.service.UnitRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitRoomServiceImpl implements UnitRoomService {

    private final RoomDaoService roomDaoService;
    private final RoomMapper roomMapper;
    private final UnitDaoService unitDaoService;

    @Override
    public ResponseEntity<Page<RoomGetResource>> retrieveUnitRooms(String unitId, Pageable pageable) {
        Specification<RoomModel> specification = Specification.where(RoomSpecification.withUnitIdEqual(unitId));
        Page<RoomModel> roomModelPage = roomDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(roomModelPage.map(roomMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<RoomGetResource> createUnitRoom(String unitId, RoomPostResource roomPostResource) {
        UnitModel unitModel = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        RoomModel roomModel = roomMapper.postResourceToModel(roomPostResource);
        roomModel.setUnit(unitModel);
        if (roomPostResource.getBathroomId() != null) {
            if (roomPostResource.getBathroomId().isBlank()) {
                roomModel.setBathroom(null);
            } else {
                RoomModel bathroom = roomDaoService.findOneBy(RoomSpecification.withIdEqual(roomPostResource.getBathroomId()).and(RoomSpecification.withUnitIdEqual(unitId)).and(RoomSpecification.withTypeEqual(RoomTypeEnum.BATHROOM)));
                roomModel.setBathroom(bathroom);
            }
        }
        roomModel = roomDaoService.save(roomModel);
        return ResponseEntity.ok(roomMapper.modelToGetResource(roomModel));
    }

    @Override
    public ResponseEntity<RoomGetResource> updateUnitRooms(String unitId, String roomId, RoomPatchResource roomPatchResource) {
        RoomModel roomModel = roomDaoService.findOneBy(RoomSpecification.withUnitIdEqual(unitId).and(RoomSpecification.withIdEqual(roomId)));
        roomModel = roomMapper.patchResourceToModel(roomPatchResource, roomModel);
        if (roomPatchResource.getBathroomId() != null) {
            if (roomPatchResource.getBathroomId().isBlank()) {
                roomModel.setBathroom(null);
            } else {
                RoomModel bathroom = roomDaoService.findOneBy(RoomSpecification.withIdEqual(roomPatchResource.getBathroomId()).and(RoomSpecification.withUnitIdEqual(unitId)).and(RoomSpecification.withTypeEqual(RoomTypeEnum.BATHROOM)));
                roomModel.setBathroom(bathroom);
            }
        }
        roomModel = roomDaoService.save(roomModel);
        return ResponseEntity.ok(roomMapper.modelToGetResource(roomModel));
    }

    @Override
    public ResponseEntity<Void> deleteUnitRoomById(String unitId, String roomId) {
        RoomModel room = roomDaoService.findOneBy(RoomSpecification.withIdEqual(roomId).and(RoomSpecification.withUnitIdEqual(unitId)));
        roomDaoService.delete(room);
        return ResponseEntity.noContent().build();
    }
}
