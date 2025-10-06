/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.enumeration.converter;

import com.smsmode.unit.enumeration.AmenityEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 12 Jun 2025</p>
 */
@Converter
public class AmenityEnumSetToStringConverter implements AttributeConverter<Set<AmenityEnum>, String> {

    @Override
    public String convertToDatabaseColumn(Set<AmenityEnum> amenityEnums) {
        if (CollectionUtils.isEmpty(amenityEnums)) {
            return "";
        }
        return amenityEnums.stream().map(a -> String.valueOf(a.ordinal())).collect(Collectors.joining(","));
    }

    @Override
    public Set<AmenityEnum> convertToEntityAttribute(String dbData) {
        if (ObjectUtils.isEmpty(dbData)) {
            return new LinkedHashSet<>();
        }
        return Arrays.stream(dbData.split(",")).map(s -> AmenityEnum.values()[Integer.parseInt(s)]).collect(Collectors.toSet());
    }
}
