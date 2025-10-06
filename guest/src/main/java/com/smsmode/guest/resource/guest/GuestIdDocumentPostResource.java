/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.resource.guest;

import com.smsmode.guest.enumeration.IdDocumentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 29 Jun 2025</p>
 */
@Data
public class GuestIdDocumentPostResource {
    @NotNull
    private IdDocumentTypeEnum type;
    @NotBlank
    private String value;
    @NotNull
    private LocalDate expirationDate;
}
