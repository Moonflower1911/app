package com.smsmode.unit.resource.inventory;

import lombok.Data;

import java.util.List;

@Data
public class UnitPricingGetResource {
    private String id;
    private List<NightRatesResource> nightRates;
    private double nightlyRate;
    private double totalAmount;
    private int minStay;
    private int maxStay;
}
