package com.smsmode.gateway.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Mar 2025</p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TOKEN")
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID")
    protected String uuid;

    @ToString.Exclude
    @Column(columnDefinition = "TEXT")
    private String value;
}
