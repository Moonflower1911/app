/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.service.impl;

import com.smsmode.guest.dao.service.SegmentDaoService;
import com.smsmode.guest.dao.specification.SegmentSpecification;
import com.smsmode.guest.exception.ConflictException;
import com.smsmode.guest.exception.enumeration.ConflictExceptionTitleEnum;
import com.smsmode.guest.mapper.SegmentMapper;
import com.smsmode.guest.model.SegmentModel;
import com.smsmode.guest.resource.segment.SegmentGetResource;
import com.smsmode.guest.resource.segment.SegmentItemGetResource;
import com.smsmode.guest.resource.segment.SegmentPatchResource;
import com.smsmode.guest.resource.segment.SegmentPostResource;
import com.smsmode.guest.service.SegmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SegmentServiceImpl implements SegmentService {

    private final SegmentMapper segmentMapper;
    private final SegmentDaoService segmentDaoService;

    @Override
    public ResponseEntity<Page<SegmentItemGetResource>> retrieveAllByPage(String search, Boolean withParent, Boolean enabled, Pageable pageable) {
        log.debug("Building segment specification with search value: {} ...", search);
        Specification<SegmentModel> specification = Specification
                .where(SegmentSpecification.withNameLike(search))
                .and(SegmentSpecification.withParent(withParent))
                .and(SegmentSpecification.withEnabled(enabled));
        log.debug("Retrieve page: {} of segment(s) from database ...", pageable.getPageNumber());
        Page<SegmentModel> segmentModelPage = segmentDaoService.findAllBy(specification, pageable);
        log.info("Segments retrieved from database successfully. Returned {} elements.", segmentModelPage.getSize());
        log.debug("Mapping segment models to item get resources ...");
        Page<SegmentItemGetResource> segmentItemGetResources = segmentModelPage.map(segmentMapper::modelToItemGetResource);
        log.info("Segment models mapped to item get resources successfully.");
        return ResponseEntity.ok(segmentItemGetResources);
    }

    @Override
    public ResponseEntity<SegmentGetResource> retrieveById(String segmentId) {
        log.debug("Retrieving segment with Id: {} from database ...", segmentId);
        SegmentModel segmentModel = segmentDaoService.findOneBy(SegmentSpecification.withIdEqual(segmentId));
        log.info("Segment retrieved successfully");
        log.debug("Mapping segment model to get resource ...");
        SegmentGetResource segmentGetResource = segmentMapper.modelToGetResource(segmentModel);
        log.info("Segment model mapped to get resource successfully. {}", segmentGetResource);
        return ResponseEntity.ok(segmentGetResource);
    }

    @Override
    public ResponseEntity<SegmentGetResource> create(SegmentPostResource segmentPostResource) {
        SegmentModel parentSegment = null;
        if (!ObjectUtils.isEmpty(segmentPostResource.getParentId())) {
            log.debug("Parent segment Id is provided, will retrieve it from database ...");
            parentSegment = segmentDaoService.findOneBy(
                    SegmentSpecification.withIdEqual(segmentPostResource.getParentId())
                            .and(SegmentSpecification.withParent(false)));
        }
        log.debug("Mapping segment post resource to model ...");
        SegmentModel segmentModel = segmentMapper.postResourceToModel(segmentPostResource);
        log.info("Segment model result after mapping: {}", segmentModel);
        if (!ObjectUtils.isEmpty(parentSegment)) {
            log.debug("Linking parent segment to model ...");
            segmentModel.setParent(parentSegment);
            if (!parentSegment.isEnabled()) {
                log.debug("Parent segment is disabled, therefore the sub segment will be disabled ...");
                segmentModel.setEnabled(false);
            }
        }
        log.debug("Will persist segment model: {} to database ...", segmentModel);
        segmentModel = segmentDaoService.save(segmentModel);
        log.info("Segment model successfully saved to database under Id: {}", segmentModel.getId());
        log.debug("Mapping segment model to Get resource ...");
        SegmentGetResource segmentGetResource = segmentMapper.modelToGetResource(segmentModel);
        log.info("Segment model result after mapping: {}", segmentGetResource);
        return ResponseEntity.created(URI.create("")).body(segmentGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<SegmentGetResource> updateById(String segmentId, SegmentPatchResource segmentPatchResource) {
        log.debug("Retrieving segment model from database by Id: {} ...", segmentId);
        SegmentModel segmentModel = segmentDaoService.findOneBy(SegmentSpecification.withIdEqual(segmentId));
        log.info("Segment model retrieved successfully");
        this.validateSegmentParenting(segmentId, segmentPatchResource.getParentId());
        //TODO: check if there's any price plans related to this segment in case of major changes
        log.debug("Mapping patch resource to segment model ...");
        segmentModel = segmentMapper.patchResourceToModel(segmentPatchResource, segmentModel);
        log.info("Segment model after mapping: {}", segmentModel);
        if (segmentPatchResource.getParentId() != null) {
            log.debug("Segment parent Id is provided.");
            if (segmentPatchResource.getParentId().isBlank()) {
                log.debug("Segment parent Id is blank. Will remove link with parent from model ...");
                segmentModel.setParent(null);
            } else {
                SegmentModel parentSegment = null;
                if (!ObjectUtils.isEmpty(segmentPatchResource.getParentId())) {
                    log.debug("Retrieve parent segment from database ...");
                    parentSegment = segmentDaoService.findOneBy(
                            SegmentSpecification.withIdEqual(segmentPatchResource.getParentId())
                                    .and(SegmentSpecification.withParent(false)));
                    log.info("Parent segment retrieved successfully");
                    log.debug("Linking parent segment to mode ...");
                    segmentModel.setParent(parentSegment);
                    if (!parentSegment.isEnabled()) {
                        log.debug("Parent segment is disabled, therefore the model will be disabled ...");
                        segmentModel.setEnabled(false);
                    }
                }
            }
        }
        segmentModel = segmentDaoService.save(segmentModel);
        if (!segmentModel.isEnabled()) {
            segmentDaoService.disabledChildrenOfSegment(segmentId);
        }
        return ResponseEntity.ok(segmentMapper.modelToGetResource(segmentModel));
    }

    @Override
    public ResponseEntity<Void> removeById(String segmentId) {
        segmentDaoService.deleteById(segmentId);
        return ResponseEntity.noContent().build();
    }

    private void validateSegmentParenting(String segmentId, String parentSegmentId) {
        log.debug("Parent segment Id is provided. Will check if the parent segment already have a parent ...");
        if (!ObjectUtils.isEmpty(parentSegmentId) && !parentSegmentId.isBlank()) {
            if (segmentDaoService.existsBy(SegmentSpecification.withIdEqual(parentSegmentId).and(SegmentSpecification.withParent(true)))) {
                log.warn("The parent segment id provided already have a parent, therefore it can't have siblings. Will throw an error ...");
                throw new ConflictException(ConflictExceptionTitleEnum.SEGMENT_PARENT_LIMIT, "Segment parent already have a parent and can't have children");
            }
            if (!ObjectUtils.isEmpty(segmentId)) {
                if (segmentId.equals(parentSegmentId)) {
                    throw new ConflictException(ConflictExceptionTitleEnum.SEGMENT_CIRCULAR_TREE, "You can't assign a segment to itself as a parent");
                }
                if (segmentDaoService.existsBy(SegmentSpecification.withParentIdEqual(segmentId))) {
                    throw new ConflictException(ConflictExceptionTitleEnum.SEGMENT_PARENT_LIMIT, "This segment is already defined as a parent and therefore can't have a parent");
                }
            }
        }


    }
}
