package com.smsmode.unit.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
public class BasePricingEmbeddable {
    @NotNull(message = "Nightly rate is required")
    @Digits(integer = 10, fraction = 2, message = "Nightly rate must have up to two decimal places")
    @DecimalMin(value = "0.0", inclusive = false, message = "Nightly rate must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal nightly;

    @Digits(integer = 10, fraction = 2, message = "Weekend night rate must have up to two decimal places")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weekend night rate must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal weekendNight;

    @Digits(integer = 10, fraction = 2, message = "Weekly rate must have up to two decimal places")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weekly rate rate must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal weekly;

    @Digits(integer = 10, fraction = 2, message = "monthly rate must have up to two decimal places")
    @DecimalMin(value = "0.0", inclusive = false, message = "monthly rate must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal monthly;

    @NotNull(message = "Minimum stay is required")
    @Positive(message = "Minimum stay must be positive")
    private Integer minStay;

    @Positive(message = "Maximum stay must be positive")
    private Integer maxStay;
}

