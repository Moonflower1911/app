/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.model;

import com.smsmode.booking.embeddable.BookingOccupancyEmbeddable;
import com.smsmode.booking.embeddable.ContactEmbeddable;
import com.smsmode.booking.embeddable.PartyRefEmbeddable;
import com.smsmode.booking.embeddable.RefEmbeddable;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing a Guest in the PMS system.
 * A guest is a person who makes a reservation at the hotel.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "BOOKING")
public class BookingModel extends AbstractBaseModel {


    private String reference;
    @Column(name = "checkin_date")
    private LocalDate checkinDate;
    @Column(name = "checkout_date")
    private LocalDate checkoutDate;
    @Column(name = "checked_in_date")
    private LocalDate checkedInDate;
    @Column(name = "checked_out_date")
    private LocalDate checkedOutDate;
    @Embedded
    private ContactEmbeddable contact;
    @Embedded
    private PartyRefEmbeddable partyRef;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",
                    column = @Column(name = "unit_id")),
            @AttributeOverride(name = "name",
                    column = @Column(name = "unit_name"))
    })
    private RefEmbeddable unitRef;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",
                    column = @Column(name = "room_id")),
            @AttributeOverride(name = "name",
                    column = @Column(name = "room_name"))
    })
    private RefEmbeddable roomRef;
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status = BookingStatusEnum.DRAFT;
    @Enumerated(EnumType.STRING)
    private BookingTypeEnum type = BookingTypeEnum.SINGLE;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private BookingModel parent;
    @Column(name = "nightly_rate")
    private BigDecimal nightlyRate;
    @Column(name = "vat_percentage")
    private BigDecimal vatPercentage;
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    @Embedded
    private BookingOccupancyEmbeddable occupancy;

}