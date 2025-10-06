package com.smsmode.task.dao.service;

import com.smsmode.task.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public interface CategoryDaoService {
    CategoryModel findOneBy(Specification<CategoryModel> specification);

    CategoryModel save(CategoryModel categoryModel);

    void delete(CategoryModel categoryModel);

    Page<CategoryModel> findAllBy(Specification<CategoryModel> specification, Pageable pageable);

    Set<CategoryModel> findAllByIdIn(Set<String> ids);
}