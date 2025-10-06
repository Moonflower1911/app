/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.RateModel;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public interface RateDaoService {

    List<RateModel> findRates(String ratePlanId, List<String> unitIds, LocalDate startDate, LocalDate endDate);

    List<RateModel> saveAll(List<RateModel> toSave);

}
