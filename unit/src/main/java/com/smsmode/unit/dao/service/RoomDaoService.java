/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service;

import com.smsmode.unit.model.RoomModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Jun 2025</p>
 */
public interface RoomDaoService {

    RoomModel findOneBy(Specification<RoomModel> specification);

    Page<RoomModel> findAllBy(Specification<RoomModel> specification, Pageable unpaged);

    RoomModel save(RoomModel roomModel);

    void deleteBy(Specification<RoomModel> specification);

    void delete(RoomModel room);

    List<RoomModel> findByUnit(String unitId);
}
