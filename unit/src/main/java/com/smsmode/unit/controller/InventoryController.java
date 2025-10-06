package com.smsmode.unit.controller;

import com.smsmode.unit.resource.inventory.get.UnitInventoryGetResource;
import com.smsmode.unit.resource.inventory.post.InventoryPostResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;

@RequestMapping("/inventory")
public interface InventoryController {

    @PostMapping
    ResponseEntity<Page<UnitInventoryGetResource>> getAvailability(@RequestBody InventoryPostResource inventoryPostResource, Pageable pageable);

}
