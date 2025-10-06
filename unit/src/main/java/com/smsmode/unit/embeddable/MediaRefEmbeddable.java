package com.smsmode.unit.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class MediaRefEmbeddable {
    private String uuid;
}
