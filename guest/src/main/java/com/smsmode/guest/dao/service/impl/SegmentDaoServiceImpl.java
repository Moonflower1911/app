/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.dao.service.impl;

import com.smsmode.guest.dao.repository.SegmentRepository;
import com.smsmode.guest.dao.service.SegmentDaoService;
import com.smsmode.guest.exception.ResourceNotFoundException;
import com.smsmode.guest.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.guest.model.SegmentModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SegmentDaoServiceImpl implements SegmentDaoService {

    private final SegmentRepository segmentRepository;

    @Override
    public Page<SegmentModel> findAllBy(Specification<SegmentModel> specification, Pageable pageable) {
        return segmentRepository.findAll(specification, pageable);
    }

    @Override
    public SegmentModel findOneBy(Specification<SegmentModel> specification) {
        return segmentRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any segment with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.SEGMENT_NOT_FOUND,
                            "No segment found with the specified criteria");
                });
    }

    @Override
    public boolean existsBy(Specification<SegmentModel> specification) {
        return segmentRepository.exists(specification);
    }

    @Override
    public SegmentModel save(SegmentModel segmentModel) {
        return segmentRepository.save(segmentModel);
    }

    @Override
    public void deleteById(String segmentId) {
        segmentRepository.deleteById(segmentId);
    }

    @Override
    @Transactional
    public void disabledChildrenOfSegment(String segmentId) {
        segmentRepository.disableSegmentsByParentId(segmentId);
    }
}
