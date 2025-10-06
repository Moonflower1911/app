package com.smsmode.booking.mapper;

import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.model.BookingModel;
import com.smsmode.booking.resource.booking.get.BookingGetResource;
import com.smsmode.booking.resource.booking.get.BookingItemGetResource;
import com.smsmode.booking.resource.booking.patch.BookingPatchResource;
import com.smsmode.booking.resource.booking.post.BookingItemPostResource;
import com.smsmode.booking.resource.booking.post.BookingPostResource;
import com.smsmode.booking.resource.common.StayResource;
import com.smsmode.booking.service.impl.BookingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mapper for BookingModel and resources
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jul 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class BookingMapper {

    @Mapping(target = "type", expression = "java(com.smsmode.booking.enumeration.BookingTypeEnum.GROUP)")
    public abstract BookingModel postResourceToModel(BookingPostResource bookingPostResource);

    @AfterMapping()
    public void afterPostResourceToModel(BookingPostResource bookingPostResource, @MappingTarget BookingModel bookingModel) {
        bookingModel.setReference(this.generateRef());
    }

    public abstract BookingModel itemPostResourceToModel(BookingItemPostResource bookingItemPostResource);

    @AfterMapping()
    public void afterItemPostResourceToModel(BookingItemPostResource bookingItemPostResource, @MappingTarget BookingModel bookingModel) {
        bookingModel.setReference(this.generateRef());
    }

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "stay", ignore = true)
    public abstract BookingGetResource modelToGetResource(BookingModel parentBooking);

    @AfterMapping
    public void afterModelToGetResource(BookingModel bookingModel, @MappingTarget BookingGetResource bookingGetResource) {
        if (bookingModel.getType().equals(BookingTypeEnum.GROUP)) {
            bookingGetResource.setStay(null);
        } else {
            StayResource stayResource = new StayResource();
            stayResource.setCheckinDate(bookingModel.getCheckinDate());
            stayResource.setCheckoutDate(bookingModel.getCheckoutDate());
            stayResource.setRoomRef(bookingModel.getRoomRef());
            stayResource.setUnitRef(bookingModel.getUnitRef());
            stayResource.setOccupancy(bookingModel.getOccupancy());
            stayResource.setTotalPrice(BookingServiceImpl.buildRateFromUnitPriceInclTax(bookingModel.getNightlyRate(), bookingModel.getVatPercentage(), BigDecimal.valueOf(ChronoUnit.DAYS.between(bookingModel.getCheckinDate(), bookingModel.getCheckoutDate()))));
            bookingGetResource.setStay(stayResource);
        }

    }

    public abstract BookingModel patchResourceToModel(BookingPatchResource bookingPatchResource, @MappingTarget BookingModel bookingModel);

    @AfterMapping
    public void afterPatchResourceToModel(BookingModel bookingModel, @MappingTarget BookingModel bookingModelPatch) {
        if (bookingModel.getType().equals(BookingTypeEnum.GROUP)) {
            cleanGroupBooking(bookingModel);
        }
    }

    public void cleanGroupBooking(BookingModel bookingModel) {
        bookingModel.setOccupancy(null);
        bookingModel.setNightlyRate(null);
        bookingModel.setUnitRef(null);
        bookingModel.setCheckinDate(null);
        bookingModel.setCheckoutDate(null);
    }

    public abstract BookingItemGetResource modelToItemGetResource(BookingModel bookingModel);

    public String generateRef() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        int random = ThreadLocalRandom.current().nextInt(100, 1000); // 3-digit random number
        return timestamp + "-" + random;
    }
}