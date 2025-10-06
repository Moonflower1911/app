/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.dao.service;

import com.smsmode.guest.model.SegmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
public interface SegmentDaoService {

    Page<SegmentModel> findAllBy(Specification<SegmentModel> specification, Pageable pageable);

    SegmentModel findOneBy(Specification<SegmentModel> specification);

    boolean existsBy(Specification<SegmentModel> specification);

    SegmentModel save(SegmentModel segmentModel);

    void deleteById(String segmentId);

    void disabledChildrenOfSegment(String segmentId);

}
