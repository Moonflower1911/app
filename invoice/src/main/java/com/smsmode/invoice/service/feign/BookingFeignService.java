package com.smsmode.invoice.service.feign;

import com.smsmode.invoice.resource.booking.get.BookingGetResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "booking", path = "/bookings")
public interface BookingFeignService {

    @GetMapping(value = "{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookingGetResource> getById(@PathVariable("bookingId") String bookingId);

}
