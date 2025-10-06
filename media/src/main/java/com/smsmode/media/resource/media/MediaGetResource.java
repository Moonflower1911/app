package com.smsmode.media.resource.media;

import com.smsmode.media.resource.common.AuditGetResource;
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