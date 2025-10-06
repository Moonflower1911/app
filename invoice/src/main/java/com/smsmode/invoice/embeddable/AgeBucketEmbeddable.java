package com.smsmode.invoice.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AgeBucketEmbeddable {
    private Integer fromAge;
    private Integer toAge;
}
