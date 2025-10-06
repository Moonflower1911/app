/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service;

import com.smsmode.unit.model.ImageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
public interface ImageDaoService {

    boolean existsBy(Specification<ImageModel> specification);

    ImageModel save(ImageModel image);

    void deleteBy(Specification<ImageModel> specification);

    Page<ImageModel> findAllBy(Specification<ImageModel> specification, Pageable pageable);

    ImageModel findOneBy(Specification<ImageModel> imageModelSpecification);

}
