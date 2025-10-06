package com.smsmode.invoice.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class FeeOccupancyEmbeddable {
    private Integer adults;
    @Embedded
    private FeeChildOccupancyEmbeddable children;
}