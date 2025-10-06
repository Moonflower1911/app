/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.embeddable;

import com.smsmode.unit.enumeration.converter.DayOfWeekSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DaySpecificPricingEmbeddable {

    @NotEmpty(message = "At least one day must be selected")
    @Convert(converter = DayOfWeekSetConverter.class)
    @Column(name = "daysOfWeek", length = 20)
    private Set<DayOfWeek> daysOfWeek = new LinkedHashSet<>();

    @NotNull(message = "Nightly rate is required")
    @Digits(integer = 10, fraction = 2, message = "Nightly rate must have up to two decimal places")
    @DecimalMin(value = "0.0", inclusive = false, message = "Nightly rate must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal nightly;

    @Digits(integer = 10, fraction = 2, message = "ppPn rate must have up to two decimal places")
    @DecimalMin(value = "0.0", inclusive = false, message = "ppPn rate must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal ppPn;

    @Positive(message = "Guest count must be positive")
    private Integer guestCount;
}