package com.smsmode.task.model;


import com.smsmode.task.model.base.AbstractBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_CATEGORY")
public class CategoryModel extends AbstractBaseModel {
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<IncidentModel> incidents = new HashSet<>();

}
