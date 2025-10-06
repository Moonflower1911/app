/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.model;

import com.smsmode.pricing.model.base.AbstractRefBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "n_unit_ref", indexes = {@Index(name = "idx_unit_ref_name", columnList = "name")})
public class UnitRefModel extends AbstractRefBaseModel {

    @Column(nullable = false)
    private String name;

    @Column(updatable = false)
    private String code;

}
