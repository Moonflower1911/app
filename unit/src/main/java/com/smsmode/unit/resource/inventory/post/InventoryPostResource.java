package com.smsmode.unit.resource.inventory.post;

import com.smsmode.unit.resource.inventory.GuestsPostResource;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class InventoryPostResource {
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private GuestsPostResource guests;
    private String segmentId;
    private String subSegmentId;
    Set<String> unitIds;
}
