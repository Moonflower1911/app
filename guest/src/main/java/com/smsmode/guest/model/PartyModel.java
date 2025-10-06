/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.model;

import com.smsmode.guest.embeddable.AddressEmbeddable;
import com.smsmode.guest.embeddable.ContactEmbeddable;
import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 22 Jul 2025</p>
 */
@Getter
@Setter
@Entity
@Table(name = "NZ_PARTY")
@NoArgsConstructor
public class PartyModel extends AbstractBaseModel {

    private String name;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private PartyTypeEnum type;
    @Embedded
    private ContactEmbeddable contact;
    @Embedded
    private AddressEmbeddable address;
    @ManyToOne
    private SegmentModel segment;
}
