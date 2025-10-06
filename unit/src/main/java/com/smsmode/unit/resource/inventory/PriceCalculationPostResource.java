package com.smsmode.unit.resource.inventory;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PriceCalculationPostResource {
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private GuestsPostResource guests;
    private String segmentId;
    private String subSegmentId;
    private List<String> units;
}
