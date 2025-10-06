/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.service.impl;

import com.smsmode.booking.dao.service.BookingDaoService;
import com.smsmode.booking.dao.service.FeeDaoService;
import com.smsmode.booking.dao.specification.BookingSpecification;
import com.smsmode.booking.dao.specification.FeeSpecification;
import com.smsmode.booking.exception.ResourceNotFoundException;
import com.smsmode.booking.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.booking.mapper.FeeMapper;
import com.smsmode.booking.model.BookingModel;
import com.smsmode.booking.model.FeeModel;
import com.smsmode.booking.resource.fee.FeePatchResource;
import com.smsmode.booking.resource.fee.FeePostResource;
import com.smsmode.booking.resource.fee.get.FeeGetResource;
import com.smsmode.booking.service.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Aug 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final FeeMapper feeMapper;
    private final FeeDaoService feeDaoService;
    private final BookingDaoService bookingDaoService;

    @Override
    public ResponseEntity<FeeGetResource> createFee(FeePostResource feePostResource) {
        BookingModel bookingModel = null;
        if (!ObjectUtils.isEmpty(feePostResource.getBookingId())) {
            bookingModel = bookingDaoService.findOneBy(BookingSpecification.withIdEqual(feePostResource.getBookingId()));
        }
        FeeModel feeModel = feeMapper.postResourceToModel(feePostResource);
        feeModel.setBooking(bookingModel);
        feeModel = feeDaoService.save(feeModel);
        return ResponseEntity.created(URI.create("")).body(feeMapper.modelToGetResource(feeModel));
    }

    @Override
    public ResponseEntity<FeeGetResource> updateFeeById(String feeId, FeePatchResource feePatchResource) {
        FeeModel feeModel = feeDaoService.findOneBy(FeeSpecification.withIdEqual(feeId));
        feeModel = feeMapper.patchResourceToModel(feePatchResource, feeModel);
        feeModel = feeDaoService.save(feeModel);
        return ResponseEntity.ok(feeMapper.modelToGetResource(feeModel));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeById(String feeId) {
        if (!feeDaoService.existsBy(FeeSpecification.withIdEqual(feeId))) {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.FEE_NOT_FOUND,
                    "No fee found with the specified id");
        }
        feeDaoService.deleteBy(FeeSpecification.withIdEqual(feeId));
        return ResponseEntity.noContent().build();
    }
}
