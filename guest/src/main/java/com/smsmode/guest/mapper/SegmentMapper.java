/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.mapper;

import com.smsmode.guest.model.SegmentModel;
import com.smsmode.guest.model.base.AbstractBaseModel;
import com.smsmode.guest.resource.common.AuditGetResource;
import com.smsmode.guest.resource.segment.SegmentGetResource;
import com.smsmode.guest.resource.segment.SegmentItemGetResource;
import com.smsmode.guest.resource.segment.SegmentPatchResource;
import com.smsmode.guest.resource.segment.SegmentPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 18 Jul 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SegmentMapper {


    public abstract SegmentItemGetResource modelToItemGetResource(SegmentModel segmentModel);

    public abstract SegmentGetResource modelToGetResource(SegmentModel segmentModel);

    @AfterMapping
    public void afterModelToItemGetResource(SegmentModel segmentModel, @MappingTarget SegmentItemGetResource segmentItemGetResource) {
        segmentItemGetResource.setAudit(this.modelToAuditResource(segmentModel));
    }

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @Mapping(target = "parent", ignore = true)
    public abstract SegmentModel postResourceToModel(SegmentPostResource segmentPostResource);


    @Mapping(target = "parent", ignore = true)
    public abstract SegmentModel patchResourceToModel(SegmentPatchResource segmentPatchResource, @MappingTarget SegmentModel segmentModel);

}
