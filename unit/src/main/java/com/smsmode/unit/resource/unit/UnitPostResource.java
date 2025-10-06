/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Data
public class UnitPostResource {
    @NotBlank
    private String name;
    private String subtitle;
    private UnitNatureEnum nature = UnitNatureEnum.SINGLE;
    private AddressEmbeddable address;
    private ContactEmbeddable contact;
    private Integer quantity;
    private String subUnitPrefix;
    private List<SubUnitResource> subUnits;
}
