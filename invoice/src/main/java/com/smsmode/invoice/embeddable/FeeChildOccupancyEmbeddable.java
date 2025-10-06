package com.smsmode.invoice.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class FeeChildOccupancyEmbeddable {
    @Embedded
    private AgeBucketEmbeddable ageBucket;
    @Column(name = "children_quantity")
    private Integer quantity;

}