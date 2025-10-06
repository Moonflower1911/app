/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.resource.party;

import com.smsmode.guest.enumeration.PartyTypeEnum;
import com.smsmode.guest.resource.common.AuditGetResource;
import com.smsmode.guest.resource.segment.SegmentGetResource;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 22 Jul 2025</p>
 */
@Data
public class PartyItemGetResource {
    private String id;
    private String name;
    private PartyTypeEnum type;
    private SegmentGetResource segment;
    private AuditGetResource audit;
}
