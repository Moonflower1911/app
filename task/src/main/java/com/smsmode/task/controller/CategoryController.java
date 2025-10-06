package com.smsmode.task.controller;

import com.smsmode.task.resource.category.CategoryItemGetResource;
import com.smsmode.task.resource.category.CategoryPatchResource;
import com.smsmode.task.resource.category.CategoryPostResource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequestMapping("/categories")
public interface CategoryController {

    @GetMapping
    ResponseEntity<Page<CategoryItemGetResource>> getAllCategories(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable
    );

    @PostMapping
    ResponseEntity<CategoryItemGetResource> postCategory(@RequestBody @Valid CategoryPostResource categoryPostResource);

    @PatchMapping("/{categoryId}")
    ResponseEntity<CategoryItemGetResource> patchCategoryById(@PathVariable String categoryId, @RequestBody @Valid CategoryPatchResource categoryPatchResource);

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategoryById(@PathVariable String categoryId);
}