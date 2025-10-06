package com.smsmode.booking.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ContactEmbeddable {
    @Column(name = "contact_name")
    private String name;
    @Column(name = "contact_email")
    private String email;
    @Column(name = "contact_mobile")
    private String mobile;
}
