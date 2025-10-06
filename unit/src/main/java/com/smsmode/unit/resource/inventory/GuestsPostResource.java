package com.smsmode.unit.resource.inventory;

import lombok.Data;

import java.util.List;

@Data
public class GuestsPostResource {
    private Integer adults;
    private List<ChildPostResource> children;
}
