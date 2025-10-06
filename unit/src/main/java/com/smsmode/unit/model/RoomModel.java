/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.RoomSubTypeEnum;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jun 2025</p>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_ROOM")
public class RoomModel extends AbstractBaseModel {

    @Column(name = "NAME")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private RoomTypeEnum type;
    @Column(name = "SUB_TYPE")
    @Enumerated(EnumType.STRING)
    private RoomSubTypeEnum subType;
    @Column(name = "FLOOR_SIZE")
    private Integer floorSize;
    @Enumerated(EnumType.STRING)
    @Column(name = "FLOOR_SIZE_UNIT")
    private FloorSizeUnitEnum floorSizeUnit = FloorSizeUnitEnum.SQM;
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BATHROOM_ID")
    private RoomModel bathroom;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "X_BEDDING", joinColumns = @JoinColumn(name = "ROOM_ID"))
    private List<BedEmbeddable> beds = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private UnitModel unit;
}
