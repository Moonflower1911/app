/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.model.base.AbstractBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Sep 2025</p>
 */
@Entity
@Getter
@Setter
@Table(name = "n_property_settings")
public class PropertySettingsModel extends AbstractBaseModel {
    @Column(name = "child_min_age", nullable = false)
    private int childMinAge = 0;
    @Column(name = "child_max_age", nullable = false)
    private int childMaxAge = 11;
    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime = LocalTime.parse("15:00");
    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime = LocalTime.parse("12:00");
}
