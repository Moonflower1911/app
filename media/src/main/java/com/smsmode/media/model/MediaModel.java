package com.smsmode.media.model;

import com.smsmode.media.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_MEDIA")
public class MediaModel extends AbstractBaseModel {

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String filePath;
}