/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.dao.service;

import com.smsmode.pricing.model.UnitRefModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Sep 2025</p>
 */
public interface UnitRefDaoService {

    Page<UnitRefModel> findAllBy(Specification<UnitRefModel> specification, Pageable pageable);

    List<UnitRefModel> saveAll(List<UnitRefModel> unitRefs);

    boolean existsBy(Specification<UnitRefModel> specification);

    UnitRefModel findOneBy(Specification<UnitRefModel> specification);

    UnitRefModel save(UnitRefModel unitRefModel);

}
