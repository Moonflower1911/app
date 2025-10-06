package com.smsmode.guest.resource.media;

import com.smsmode.guest.resource.common.AuditGetResource;
import lombok.Data;

@Data
public class MediaGetResource {
    private String id;
    private String fileName;
    private Long size;
    private String type;
    private String extension;
    private String filePath;
    private AuditGetResource audit;
}