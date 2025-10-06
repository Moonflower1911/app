package com.smsmode.invoice.embeddable;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class BookingOccupancyEmbeddable {
    private Integer adults;

    @ElementCollection
    @CollectionTable(name = "BOOKING_CHILDREN", joinColumns = @JoinColumn(name = "BOOKING_ID"))
    private List<ChildEmbeddable> children = new ArrayList<>();
}