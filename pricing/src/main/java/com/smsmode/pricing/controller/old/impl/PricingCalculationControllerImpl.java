package com.smsmode.pricing.controller.old.impl;

import com.smsmode.pricing.controller.old.PricingCalculationController;
import com.smsmode.pricing.resource.old.common.UnitPricingGetResource;
import com.smsmode.pricing.resource.old.pricecalculation.PriceCalculationPostResource;
import com.smsmode.pricing.service.old.PricingCalculationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementation of PricingCalculationController for managing pricing calculation REST endpoints.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PricingCalculationControllerImpl implements PricingCalculationController {

    private final PricingCalculationService pricingCalculationService;

    @Override
    public ResponseEntity<List<UnitPricingGetResource>> calculatePricing(PriceCalculationPostResource priceCalculationPostResource) {
        log.debug("POST /price-calculations - Calculating pricing for {} units",
                priceCalculationPostResource.getUnits().size());
        return pricingCalculationService.calculatePricing(priceCalculationPostResource);
    }
}