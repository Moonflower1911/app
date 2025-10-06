/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.model;

import com.smsmode.uaa.enumeration.TokenTypeEnum;
import com.smsmode.uaa.model.base.AbstractUuidBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Dec 2024
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "N_TOKEN")
public class TokenModel extends AbstractUuidBaseModel {

    @Column(name = "ENCRYPTED_VALUE", length = 500)
    private String value;

    @Column(name = "EXPIRATION_DATE")
    private LocalDateTime expirationDate;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private TokenTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserModel login;

}

