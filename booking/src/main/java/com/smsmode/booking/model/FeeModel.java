/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.model;

import com.smsmode.booking.embeddable.FeeOccupancyEmbeddable;
import com.smsmode.booking.embeddable.FeeRefEmbeddable;
import com.smsmode.booking.enumeration.FeeModalityEnum;
import com.smsmode.booking.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "fee")
public class FeeModel extends AbstractBaseModel {
    @Embedded
    private FeeRefEmbeddable feeRef;
    @Enumerated(EnumType.STRING)
    @Column(name = "MODALITY", nullable = false)
    private FeeModalityEnum modality;
    private BigDecimal price;
    private BigDecimal vatPercentage;
    private Integer quantity;
    @Embedded
    private FeeOccupancyEmbeddable occupancy;
    @ManyToOne
    private BookingModel booking;
}
