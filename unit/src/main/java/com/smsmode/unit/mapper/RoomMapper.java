/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.enumeration.RoomSubTypeEnum;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.model.RoomModel;
import com.smsmode.unit.resource.unit.rooms.RoomGetResource;
import com.smsmode.unit.resource.unit.rooms.RoomPatchResource;
import com.smsmode.unit.resource.unit.rooms.RoomPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 15 Jun 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RoomMapper {

    @Mapping(target = "unit", ignore = true)
    public abstract RoomModel postResourceToModel(RoomPostResource roomPostResource);

    @AfterMapping
    public void afterPostResourceToModel(RoomPostResource roomPostResource, @MappingTarget RoomModel roomModel) {
        if (roomPostResource.getType().equals(RoomTypeEnum.BATHROOM) || roomPostResource.getType().equals(RoomTypeEnum.KITCHEN) || (roomPostResource.getType().equals(RoomTypeEnum.LIVING) && roomPostResource.getSubType().equals(RoomSubTypeEnum.LIVING_ROOM))) {
            roomModel.setBeds(null);
        }
        if (roomPostResource.getType().equals(RoomTypeEnum.BATHROOM) || roomPostResource.getType().equals(RoomTypeEnum.KITCHEN)) {
            roomModel.setBathroom(null);
        }
    }

    public abstract RoomGetResource modelToGetResource(RoomModel roomModel);

    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract RoomModel patchResourceToModel(RoomPatchResource roomPatchResource, @MappingTarget RoomModel roomModel);

    @AfterMapping
    public void afterPatchResourceToModel(RoomPatchResource roomPatchResource, @MappingTarget RoomModel roomModel) {
        if (RoomTypeEnum.BATHROOM.equals(roomPatchResource.getType()) || RoomTypeEnum.KITCHEN.equals(roomPatchResource.getType()) || (RoomTypeEnum.LIVING.equals(roomPatchResource.getType()) && RoomSubTypeEnum.LIVING_ROOM.equals(roomPatchResource.getSubType()))) {
            roomModel.setBeds(null);
        }
        if (RoomTypeEnum.BATHROOM.equals(roomPatchResource.getType()) || RoomTypeEnum.KITCHEN.equals(roomPatchResource.getType())) {
            roomModel.setBathroom(null);
        }
    }

}
