package com.smsmode.invoice.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ClientRefEmbeddable {
    private String id;
    private String name;
}
