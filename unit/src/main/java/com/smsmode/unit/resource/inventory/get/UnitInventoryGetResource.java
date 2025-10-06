package com.smsmode.unit.resource.inventory.get;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.enumeration.AmenityEnum;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.pricing.DatedRateGetResource;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class UnitInventoryGetResource {
    private String id;
    private String name;
    private String description;
    private UnitNatureEnum nature;
    private AvailabilityGetResource availability;
    private Double floorSize;
    private FloorSizeUnitEnum floorSizeUnit;
    private OccupancyEmbeddable maxOccupancy;
    private Set<AmenityEnum> amenities;
    private boolean childrenAllowed = true;
    private boolean eventsAllowed = false;
    private boolean smokingAllowed = false;
    private boolean petsAllowed = false;
    private List<BedEmbeddable> beds;

    // --- Updated pricing section ---
    private BigDecimal avgRateNight;           // New field: average nightly rate (instead of pricingPerDay map)
    private BigDecimal totalBookingRate;       // New field: total booking cost for the selected rate plan
    private List<DatedRateGetResource> dailyRates; // New field: nightly rate breakdown
}
