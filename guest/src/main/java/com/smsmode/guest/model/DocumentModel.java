/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.model;

import com.smsmode.guest.embeddable.MediaRefEmbeddable;
import com.smsmode.guest.enumeration.IdDocumentTypeEnum;
import com.smsmode.guest.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity representing an identification document for a guest.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NZ_DOCUMENT")
public class DocumentModel extends AbstractBaseModel {
    @Enumerated(EnumType.STRING)
    private IdDocumentTypeEnum type;

    private String value;

    private LocalDate expirationDate;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "MEDIA_ID"))
    private MediaRefEmbeddable media;

    @ManyToOne(fetch = FetchType.LAZY)
    private PartyModel party;
}