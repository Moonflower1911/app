package com.smsmode.guest.dao.service;

import com.smsmode.guest.model.PartyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PartyDaoService {
    PartyModel save(PartyModel partyModel);

    Page<PartyModel> findAllBy(Specification<PartyModel> specification, Pageable pageable);

    PartyModel findOneBy(Specification<PartyModel> specification);

    boolean existsById(String partyId);

    void deleteById(String partyId);

    void delete(PartyModel party);

}