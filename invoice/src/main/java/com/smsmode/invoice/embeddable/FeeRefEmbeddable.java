package com.smsmode.invoice.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class FeeRefEmbeddable {
    @Column(name = "fee_id")
    private String id;
    @Column(name = "fee_name")
    private String name;
    @Column(name = "fee_price")
    private String price;
}
