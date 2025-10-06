package com.smsmode.unit.service;

import com.smsmode.unit.resource.inventory.get.UnitInventoryGetResource;
import com.smsmode.unit.resource.inventory.post.InventoryPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InventoryService {
    ResponseEntity<Page<UnitInventoryGetResource>> getInventory(InventoryPostResource inventoryPostResource, Pageable pageable);
}
