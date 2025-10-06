package com.smsmode.invoice.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RefEmbeddable {
    private String id;
    private String name;
}
