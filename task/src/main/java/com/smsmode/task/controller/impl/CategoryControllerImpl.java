package com.smsmode.task.controller.impl;

import com.smsmode.task.controller.CategoryController;
import com.smsmode.task.resource.category.CategoryItemGetResource;
import com.smsmode.task.resource.category.CategoryPatchResource;
import com.smsmode.task.resource.category.CategoryPostResource;
import com.smsmode.task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Page<CategoryItemGetResource>> getAllCategories(String search, Pageable pageable) {
        return categoryService.retrieveCategoriesByPage(search, pageable);
    }

    @Override
    public ResponseEntity<CategoryItemGetResource> postCategory(CategoryPostResource categoryPostResource) {
        return categoryService.createCategory(categoryPostResource);
    }

    @Override
    public ResponseEntity<CategoryItemGetResource> patchCategoryById(String categoryId, CategoryPatchResource categoryPatchResource) {
        return categoryService.updateCategoryById(categoryId, categoryPatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(String categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }
}