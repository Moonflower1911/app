/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.enumeration.AmenityEnum;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.enumeration.converter.AmenityEnumSetToStringConverter;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "X_UNIT")
public class UnitModel extends AbstractBaseModel {
    private String name;
    @Column(unique = true, updatable = false)
    private String code;
    private String subTitle;
    @Embedded
    private AddressEmbeddable address;
    @Embedded
    private ContactEmbeddable contact;
    private boolean readiness = false;
    private String calendarColor;
    @Enumerated(EnumType.STRING)
    private UnitNatureEnum nature = UnitNatureEnum.SINGLE;
    @Enumerated(EnumType.STRING)
    private UnitTypeEnum type;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer travellerAge;
    private boolean childrenAllowed = true;
    private boolean eventsAllowed = false;
    private boolean smokingAllowed = false;
    private boolean petsAllowed = false;
    private Double floorSize;
    @Enumerated(EnumType.STRING)
    private FloorSizeUnitEnum floorSizeUnit = FloorSizeUnitEnum.SQM;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "adults",
                    column = @Column(name = "MIN_ADULTS")),
            @AttributeOverride(name = "children",
                    column = @Column(name = "MIN_CHILDREN"))
    })
    private OccupancyEmbeddable minOccupancy = new OccupancyEmbeddable(1, 0);
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "adults",
                    column = @Column(name = "MAX_ADULTS")),
            @AttributeOverride(name = "children",
                    column = @Column(name = "MAX_CHILDREN"))
    })
    private OccupancyEmbeddable maxOccupancy = new OccupancyEmbeddable(2, 0);

    @Convert(converter = AmenityEnumSetToStringConverter.class)
    private Set<AmenityEnum> amenities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private UnitModel parent;

    private Integer priority;

    public UnitModel(String name, AddressEmbeddable address, ContactEmbeddable contact, boolean readiness, Integer priority) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.readiness = readiness;
        this.priority = priority;
    }

}
