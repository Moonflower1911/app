package com.smsmode.booking.dao.service.impl;

import com.smsmode.booking.dao.repository.BookingRepository;
import com.smsmode.booking.dao.service.BookingDaoService;
import com.smsmode.booking.exception.ResourceNotFoundException;
import com.smsmode.booking.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.booking.model.BookingModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingDaoServiceImpl implements BookingDaoService {

    private final BookingRepository bookingRepository;

    @Override
    public List<String> findBookedUnitIds(LocalDate checkinDate, LocalDate checkoutDate, boolean strict) {
        if (strict) {
            return bookingRepository.findLooselyBookedUnitIds(checkinDate, checkoutDate);
        } else {
            return bookingRepository.findStrictlyBookedUnitIds(checkinDate, checkoutDate);
        }
    }

    @Override
    public List<String> findBookedUnitIdsExcludingBooking(LocalDate checkinDate, LocalDate checkoutDate,
                                                          boolean strict, String excludeBookingId) {
/*        if (strict) {
            return bookingRepository.findStrictlyBookedUnitIdsExcludingBooking(
                    checkinDate, checkoutDate, excludeBookingId);
        } else {
            return bookingRepository.findLooselyBookedUnitIdsExcludingBooking(
                    checkinDate, checkoutDate, excludeBookingId);
        }*/

        return null;
    }

    @Override
    public BookingModel save(BookingModel bookingModel) {
        return bookingRepository.save(bookingModel);
    }

    @Override
    public BookingModel findOneBy(Specification<BookingModel> specification) {
        return bookingRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any booking with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.BOOKING_NOT_FOUND,
                            "No booking found with the specified criteria");
                });
    }

    @Override
    public List<BookingModel> findAllBy(Specification<BookingModel> specification) {
        return bookingRepository.findAll(specification);
    }

    @Override
    public Page<BookingModel> findAllBy(Specification<BookingModel> specification, Pageable pageable) {
        return bookingRepository.findAll(specification, pageable);
    }

    @Override
    public void delete(BookingModel bookingModel) {
        bookingRepository.delete(bookingModel);
    }

    @Override
    public void deleteAll(Collection<BookingModel> bookingModels) {
        bookingRepository.deleteAll(bookingModels);
    }

    @Override
    public long countBy(Specification<BookingModel> specification) {
        return bookingRepository.count(specification);
    }

}