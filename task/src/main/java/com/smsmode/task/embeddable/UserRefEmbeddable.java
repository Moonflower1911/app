package com.smsmode.task.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserRefEmbeddable {
    private String id;
    private String name;
}
