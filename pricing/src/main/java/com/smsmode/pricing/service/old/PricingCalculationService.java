package com.smsmode.pricing.service.old;

import com.smsmode.pricing.resource.old.common.UnitPricingGetResource;
import com.smsmode.pricing.resource.old.pricecalculation.PriceCalculationPostResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service interface for Pricing Calculation business operations.
 */
public interface PricingCalculationService {

    /**
     * Calculates pricing for multiple units based on stay dates, guests, and segment.
     */
    ResponseEntity<List<UnitPricingGetResource>> calculatePricing(PriceCalculationPostResource request);
}