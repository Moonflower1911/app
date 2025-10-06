/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.embeddable.MediaRefEmbeddable;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "N_IMAGE")
public class ImageModel extends AbstractBaseModel {
    private boolean cover = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private UnitModel unit;

    @Embedded
    private MediaRefEmbeddable media;
}
