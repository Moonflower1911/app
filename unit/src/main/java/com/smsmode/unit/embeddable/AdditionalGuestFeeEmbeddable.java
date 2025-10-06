package com.smsmode.unit.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalGuestFeeEmbeddable {
    @Positive(message = "Fee per person per night must be positive")
    private BigDecimal feePpPn;

    @Positive(message = "Guest count must be positive")
    private Integer guestCount;
}

