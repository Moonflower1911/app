package com.smsmode.guest.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Embeddable class representing an address for a guest.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Getter
@Setter
@Embeddable
public class AddressEmbeddable {

    private String street1;

    private String street2;

    private String postCode;

    private String city;

    private String country;
}