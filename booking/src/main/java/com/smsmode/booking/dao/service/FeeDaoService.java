/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.dao.service;

import com.smsmode.booking.model.FeeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Aug 2025</p>
 */
public interface FeeDaoService {

    Page<FeeModel> findAllBy(Specification<FeeModel> specification, Pageable pageable);

    FeeModel findOneBy(Specification<FeeModel> feeModelSpecification);

    boolean existsBy(Specification<FeeModel> feeModelSpecification);


    FeeModel save(FeeModel feeModel);

    void deleteBy(Specification<FeeModel> specification);

    void deleteByBookingId(String bookingId);

    void deleteAll(Collection<FeeModel> feeModels);


}
