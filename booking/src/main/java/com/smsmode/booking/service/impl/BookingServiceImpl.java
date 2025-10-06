package com.smsmode.booking.service.impl;

import com.smsmode.booking.dao.service.BookingDaoService;
import com.smsmode.booking.dao.service.FeeDaoService;
import com.smsmode.booking.dao.specification.BookingSpecification;
import com.smsmode.booking.dao.specification.FeeSpecification;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.exception.ConflictException;
import com.smsmode.booking.exception.enumeration.ConflictExceptionTitleEnum;
import com.smsmode.booking.mapper.BookingMapper;
import com.smsmode.booking.mapper.FeeMapper;
import com.smsmode.booking.model.BookingModel;
import com.smsmode.booking.model.FeeModel;
import com.smsmode.booking.model.base.AbstractBaseModel;
import com.smsmode.booking.resource.booking.get.BookingGetResource;
import com.smsmode.booking.resource.booking.get.BookingItemGetResource;
import com.smsmode.booking.resource.booking.get.BookingRefGetResource;
import com.smsmode.booking.resource.booking.get.RateGetResource;
import com.smsmode.booking.resource.booking.patch.BookingPatchResource;
import com.smsmode.booking.resource.booking.post.BookingItemPostResource;
import com.smsmode.booking.resource.booking.post.BookingPostResource;
import com.smsmode.booking.resource.fee.FeePostResource;
import com.smsmode.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingDaoService bookingDaoService;
    private final FeeDaoService feeDaoService;
    private final BookingMapper bookingMapper;
    private final FeeMapper feeMapper;

    @Override
    public ResponseEntity<Page<BookingItemGetResource>> retrieveAllByPage(
            String search,
            BookingStatusEnum[] statuses,
            BookingTypeEnum[] types,
            LocalDate checkinDate,
            LocalDate checkoutDate,
            Pageable pageable
    ) {
        Specification<BookingModel> specification = Specification.where(BookingSpecification.withContactNameLike(search)
                        .or(BookingSpecification.withParentContactNameLike(search)
                                .or(BookingSpecification.withReferenceLike(search))))
                .and(BookingSpecification.withStatusesIn(statuses)
                        .and(BookingSpecification.withTypesIn(types))
                        .and(BookingSpecification.withDateRangeOverlap(checkinDate, checkoutDate)));
        Page<BookingModel> bookings = bookingDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(bookings.map(bookingMapper::modelToItemGetResource));
    }

    @Override
    @Transactional
    public ResponseEntity<BookingGetResource> retrieveById(String bookingId) {
        BookingModel bookingModel = bookingDaoService.findOneBy(BookingSpecification.withIdEqual(bookingId));
        BookingGetResource bookingGetResource = bookingMapper.modelToGetResource(bookingModel);
        if (bookingModel.getType().equals(BookingTypeEnum.SINGLE)) {
            List<FeeModel> feeModels = feeDaoService.findAllBy(FeeSpecification.withBookingIdEqual(bookingModel.getId()), Pageable.unpaged()).getContent();
            bookingGetResource.setFees(feeModels.stream().map(feeMapper::modelToGetResource).toList());
            if (!ObjectUtils.isEmpty(bookingModel.getParent())) {
                BookingRefGetResource bookingRefGetResource = new BookingRefGetResource();
                bookingRefGetResource.setId(bookingModel.getParent().getId());
                bookingRefGetResource.setReference(bookingModel.getParent().getReference());
                bookingGetResource.setParent(bookingRefGetResource);
            }
        } else {
            List<BookingModel> subBookings = bookingDaoService.findAllBy(BookingSpecification.withParentIdEqual(bookingId), Pageable.unpaged()).getContent();
            List<BookingGetResource> items = subBookings.stream().map(bookingMapper::modelToGetResource).toList();
            for (BookingGetResource item : items) {
                List<FeeModel> feeModels = feeDaoService.findAllBy(FeeSpecification.withBookingIdEqual(item.getId()), Pageable.unpaged()).getContent();
                item.setFees(feeModels.stream().map(feeMapper::modelToGetResource).toList());
            }
            bookingGetResource.setItems(items);
        }
        return ResponseEntity.ok(bookingGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<BookingGetResource> create(BookingPostResource bookingPostResource) {
        log.debug("Creating new booking with {} items", bookingPostResource.getItems().size());

        BookingModel parentBooking = bookingMapper.postResourceToModel(bookingPostResource);
        parentBooking = bookingDaoService.save(parentBooking);
        List<BookingModel> bookingItems = new ArrayList<>();
        Map<String, List<FeeModel>> bookingFees = new HashMap<>();
        for (BookingItemPostResource bookingItemPostResource : bookingPostResource.getItems()) {
            BookingModel bookingItem = bookingMapper.itemPostResourceToModel(bookingItemPostResource);
            bookingItem.setParent(parentBooking);
            bookingItem = bookingDaoService.save(bookingItem);
            if (!CollectionUtils.isEmpty(bookingItemPostResource.getFees())) {
                for (FeePostResource feePostResource : bookingItemPostResource.getFees()) {
                    FeeModel feeModel = feeMapper.postResourceToModel(feePostResource);
                    feeModel.setBooking(bookingItem);
                    feeModel = feeDaoService.save(feeModel);
                    if (!bookingFees.containsKey(bookingItem.getId())) {
                        bookingFees.put(bookingItem.getId(), new ArrayList<>());
                    }
                    bookingFees.getOrDefault(bookingItem.getId(), new ArrayList<>()).add(feeModel);
                }
            }

            bookingItems.add(bookingItem);
        }
        BookingGetResource bookingGetResource = bookingMapper.modelToGetResource(parentBooking);
        bookingGetResource.setItems(bookingItems.stream().map(bookingMapper::modelToGetResource).toList());
        if (!CollectionUtils.isEmpty(bookingFees)) {
            bookingGetResource.getItems().forEach(item -> {
                item.setFees(bookingFees.get(item.getId()).stream().map(feeMapper::modelToGetResource).toList());
            });
        }
        return ResponseEntity.created(URI.create("")).body(bookingGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<BookingGetResource> addBooking(String bookingId, BookingItemPostResource bookingItemPostResource) {
        BookingModel parent = bookingDaoService.findOneBy(BookingSpecification.withIdEqual(bookingId));
        BookingModel bookingModel = bookingMapper.itemPostResourceToModel(bookingItemPostResource);
        bookingModel.setParent(parent);
        bookingModel = bookingDaoService.save(bookingModel);
        List<FeeModel> bookingFees = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bookingItemPostResource.getFees())) {
            for (FeePostResource feePostResource : bookingItemPostResource.getFees()) {
                FeeModel feeModel = feeMapper.postResourceToModel(feePostResource);
                feeModel.setBooking(bookingModel);
                feeModel = feeDaoService.save(feeModel);
                bookingFees.add(feeModel);
            }
        }
        BookingGetResource bookingGetResource = bookingMapper.modelToGetResource(bookingModel);
        if (!CollectionUtils.isEmpty(bookingFees)) {
            bookingGetResource.setFees(bookingFees.stream().map(feeMapper::modelToGetResource).toList());
        }
        return ResponseEntity.created(URI.create("")).body(bookingGetResource);
    }

    @Override
    @Transactional
    public ResponseEntity<BookingGetResource> updateById(String bookingId, BookingPatchResource bookingPatchResource) {
        List<BookingModel> bookingModels;
        BookingModel bookingModel = bookingDaoService.findOneBy(BookingSpecification.withIdEqual(bookingId));
        if (bookingModel.getType().equals(BookingTypeEnum.SINGLE)) {
            bookingPatchResource.setStatus(null);
        } else {
            bookingPatchResource.setNightlyRate(null);
        }
        bookingModel = bookingMapper.patchResourceToModel(bookingPatchResource, bookingModel);
        bookingModel = bookingDaoService.save(bookingModel);
        BookingGetResource bookingGetResource = bookingMapper.modelToGetResource(bookingModel);
        if (bookingModel.getType().equals(BookingTypeEnum.GROUP) && !ObjectUtils.isEmpty(bookingPatchResource.getApplyStatusToAll()) && Boolean.TRUE.equals(bookingPatchResource.getApplyStatusToAll())) {
            bookingModels = bookingDaoService.findAllBy(BookingSpecification.withParentIdEqual(bookingId), Pageable.unpaged()).getContent();
            for (BookingModel subBooking : bookingModels) {
                subBooking.setStatus(bookingModel.getStatus());
                bookingDaoService.save(subBooking);
            }
            bookingGetResource.setItems(bookingModels.stream().map(bookingMapper::modelToGetResource).toList());
        }
        return ResponseEntity.ok(bookingMapper.modelToGetResource(bookingModel));
    }

    @Override
    public ResponseEntity<Void> removeById(String bookingId) {
        BookingModel bookingModel = bookingDaoService.findOneBy(BookingSpecification.withIdEqual(bookingId));
        if (!BookingStatusEnum.DRAFT.equals(bookingModel.getStatus())) {
            log.debug("Booking has status: {}, will throw an error ...", bookingModel.getStatus());
            throw new ConflictException(ConflictExceptionTitleEnum.BOOKING_CANNOT_BE_DELETED, "Only booking with status DRAFT can be deleted");
        }
        if (bookingModel.getType().equals(BookingTypeEnum.SINGLE)) {
            List<FeeModel> feeModels = feeDaoService.findAllBy(FeeSpecification.withBookingIdIn(Stream.of(bookingId).collect(Collectors.toSet())), Pageable.unpaged()).getContent();
            if (!CollectionUtils.isEmpty(feeModels)) {
                feeDaoService.deleteAll(feeModels);
            }
            bookingDaoService.delete(bookingModel);
        } else {
            List<BookingModel> bookingModels = bookingDaoService.findAllBy(BookingSpecification.withParentIdEqual(bookingId), Pageable.unpaged()).getContent();
            Set<String> bookingIds = bookingModels.stream().map(AbstractBaseModel::getId).collect(Collectors.toSet());
            bookingIds.add(bookingId);
            List<FeeModel> feeModels = feeDaoService.findAllBy(FeeSpecification.withBookingIdIn(bookingIds), Pageable.unpaged()).getContent();
            if (!CollectionUtils.isEmpty(feeModels)) {
                feeDaoService.deleteAll(feeModels);
            }
            bookingDaoService.deleteAll(bookingModels);
            bookingDaoService.delete(bookingModel);
        }
        return ResponseEntity.noContent().build();
    }

    public static RateGetResource buildRateFromUnitPriceInclTax(BigDecimal unitAveragePriceInclTax, BigDecimal vatPercentage, BigDecimal quantity) {
        // Ensure scale for percentage
        vatPercentage = vatPercentage.setScale(2, RoundingMode.HALF_UP);

        // Calculate VAT factor
        BigDecimal vatFactor = vatPercentage.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);

        // Calculate unit price excl. tax
        BigDecimal unitPriceExclTax = unitAveragePriceInclTax.divide(BigDecimal.ONE.add(vatFactor), 6, RoundingMode.HALF_UP);

        // Calculate VAT amount per unit
        BigDecimal vatAmount = unitAveragePriceInclTax.subtract(unitPriceExclTax);

        // Total excl. tax
        BigDecimal amountExclTax = unitPriceExclTax.multiply(quantity);

        // Total incl. tax
        BigDecimal amountInclTax = unitAveragePriceInclTax.multiply(quantity);

        // Build object
        RateGetResource rate = new RateGetResource();
        rate.setUnitPriceExclTax(unitPriceExclTax.setScale(2, RoundingMode.HALF_UP));
        rate.setQuantity(quantity.setScale(2, RoundingMode.HALF_UP));
        rate.setVatPercentage(vatPercentage);
        rate.setVatAmount(vatAmount.setScale(2, RoundingMode.HALF_UP));
        rate.setUnitPriceInclTax(unitAveragePriceInclTax.setScale(2, RoundingMode.HALF_UP));
        rate.setAmountExclTax(amountExclTax.setScale(2, RoundingMode.HALF_UP));
        rate.setAmountInclTax(amountInclTax.setScale(2, RoundingMode.HALF_UP));

        return rate;
    }

}