package com.smsmode.unit.service.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "booking")
public interface BookingFeignService {

    @GetMapping("/internal/booked-units")
    ResponseEntity<List<String>> getBookedUnits(
            @RequestParam("checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate,
            @RequestParam("checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate
    );
}
