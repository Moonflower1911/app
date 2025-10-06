package com.smsmode.guest.dao.service.impl;

import com.smsmode.guest.dao.repository.PartyRepository;
import com.smsmode.guest.dao.service.PartyDaoService;
import com.smsmode.guest.exception.ResourceNotFoundException;
import com.smsmode.guest.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.guest.model.PartyModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyDaoServiceImpl implements PartyDaoService {

    private final PartyRepository partyRepository;

    @Override
    public PartyModel save(PartyModel guestModel) {
        return partyRepository.save(guestModel);
    }

    @Override
    public Page<PartyModel> findAllBy(Specification<PartyModel> specification, Pageable pageable) {
        return partyRepository.findAll(specification, pageable);
    }

    @Override
    public PartyModel findOneBy(Specification<PartyModel> specification) {
        return partyRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any party with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.PARTY_NOT_FOUND,
                            "No party found with the specified criteria");
                });
    }

    @Override
    public boolean existsById(String partyId) {
        return partyRepository.existsById(partyId);
    }

    @Override
    public void deleteById(String partyId) {
        partyRepository.deleteById(partyId);
    }

    @Override
    public void delete(PartyModel party) {
        partyRepository.delete(party);
    }
}