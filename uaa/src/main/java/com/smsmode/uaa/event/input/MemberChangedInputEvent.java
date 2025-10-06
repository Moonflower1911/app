/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.event.input;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an event containing details about changes to a member's information. This class is
 * typically used for event-driven systems to propagate member updates.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Nov 2024
 */
@Data
@AllArgsConstructor
public class MemberChangedInputEvent {
    private String firstName;
    private String lastName;
    private String memberId;
    private String userId;
}
