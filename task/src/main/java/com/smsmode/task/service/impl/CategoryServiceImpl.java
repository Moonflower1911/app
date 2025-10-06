package com.smsmode.task.service.impl;

import com.smsmode.task.dao.service.CategoryDaoService;
import com.smsmode.task.dao.specification.CategorySpecification;
import com.smsmode.task.mapper.CategoryMapper;
import com.smsmode.task.model.CategoryModel;
import com.smsmode.task.resource.category.CategoryItemGetResource;
import com.smsmode.task.resource.category.CategoryPatchResource;
import com.smsmode.task.resource.category.CategoryPostResource;
import com.smsmode.task.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDaoService categoryDaoService;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<Page<CategoryItemGetResource>> retrieveCategoriesByPage(String search, Pageable pageable) {
        log.debug("Retrieving categories with search: {} and pageable: {}", search, pageable);

        Specification<CategoryModel> specification = null;
        if (!ObjectUtils.isEmpty(search)) {
            specification = CategorySpecification.withNameLike(search);
        }

        Page<CategoryModel> categoryModels = categoryDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(categoryModels.map(categoryMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<CategoryItemGetResource> createCategory(CategoryPostResource categoryPostResource) {
        log.debug("Creating new category with name: {}", categoryPostResource.getName());

        // Convert resource to model
        CategoryModel categoryModel = categoryMapper.postResourceToModel(categoryPostResource);

        // Save to database
        categoryModel = categoryDaoService.save(categoryModel);

        log.info("Category created successfully with id: {}", categoryModel.getId());

        // Convert back to resource and return
        return ResponseEntity.ok(categoryMapper.modelToGetResource(categoryModel));
    }

    @Override
    public ResponseEntity<CategoryItemGetResource> updateCategoryById(String categoryId, CategoryPatchResource categoryPatchResource) {
        log.debug("Updating category with id: {} and name: {}", categoryId, categoryPatchResource.getName());

        // Find existing category
        CategoryModel existingCategory = categoryDaoService.findOneBy(CategorySpecification.withIdEqual(categoryId));

        // Update category with new data
        existingCategory = categoryMapper.patchResourceToModel(categoryPatchResource, existingCategory);

        // Save updated category
        existingCategory = categoryDaoService.save(existingCategory);

        log.info("Category updated successfully with id: {}", existingCategory.getId());

        // Convert back to resource and return
        return ResponseEntity.ok(categoryMapper.modelToGetResource(existingCategory));
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(String categoryId) {
        log.debug("Deleting category with id: {}", categoryId);

        // Find existing category
        CategoryModel existingCategory = categoryDaoService.findOneBy(CategorySpecification.withIdEqual(categoryId));

        // Delete category
        categoryDaoService.delete(existingCategory);

        log.info("Category deleted successfully with id: {}", categoryId);

        // Return empty response with 200 status
        return ResponseEntity.ok().build();
    }
}