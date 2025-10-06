/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service.impl;

import com.smsmode.pricing.dao.repository.RateRepository;
import com.smsmode.pricing.dao.service.RateDaoService;
import com.smsmode.pricing.model.RateModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateDaoServiceImpl implements RateDaoService {

    private final RateRepository rateRepository;

    @Override
    public List<RateModel> findRates(String ratePlanId, List<String> unitIds, LocalDate startDate, LocalDate endDate) {
        return rateRepository.findRates(ratePlanId, unitIds, startDate, endDate);
    }

    @Override
    public List<RateModel> saveAll(List<RateModel> rateModels) {
        return rateRepository.saveAll(rateModels);
    }
}
