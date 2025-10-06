/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.guest.resource.image;

import com.smsmode.guest.resource.common.AuditGetResource;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 20 May 2025</p>
 */
@Data
public class ImageGetResource {
    private String id;
    private boolean cover;
    private String fileName;
    private AuditGetResource audit;
}
