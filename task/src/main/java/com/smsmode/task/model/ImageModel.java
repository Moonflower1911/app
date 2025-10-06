package com.smsmode.task.model;

import com.smsmode.task.model.base.AbstractBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_IMAGE")
public class ImageModel extends AbstractBaseModel {
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private IncidentModel incident;
}
