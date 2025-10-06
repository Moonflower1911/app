package com.smsmode.task.resource.image;

import com.smsmode.task.resource.common.AuditGetResource;
import lombok.Data;

@Data
public class ImageGetResource {
    private String id;
    private String fileName;
    private AuditGetResource audit;
}
