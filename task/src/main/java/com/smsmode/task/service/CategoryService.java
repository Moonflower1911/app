package com.smsmode.task.service;

import com.smsmode.task.resource.category.CategoryItemGetResource;
import com.smsmode.task.resource.category.CategoryPatchResource;
import com.smsmode.task.resource.category.CategoryPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<Page<CategoryItemGetResource>> retrieveCategoriesByPage(String search, Pageable pageable);

    ResponseEntity<CategoryItemGetResource> createCategory(CategoryPostResource categoryPostResource);

    ResponseEntity<CategoryItemGetResource> updateCategoryById(String categoryId, CategoryPatchResource categoryPatchResource);

    ResponseEntity<Void> deleteCategoryById(String categoryId);
}