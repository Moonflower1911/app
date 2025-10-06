package com.smsmode.task.dao.service.Impl;

import com.smsmode.task.dao.repository.CategoryRepository;
import com.smsmode.task.dao.service.CategoryDaoService;
import com.smsmode.task.exception.ResourceNotFoundException;
import com.smsmode.task.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.task.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryDaoServiceImpl implements CategoryDaoService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryModel findOneBy(Specification<CategoryModel> specification){
        return categoryRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any category with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.CATEGORY_NOT_FOUND,
                            "No category found with the specified criteria");
                });
    }

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        log.debug("Saving category: {}", categoryModel.getName());
        return categoryRepository.save(categoryModel);
    }

    @Override
    public void delete(CategoryModel categoryModel) {
        log.debug("Deleting category: {}", categoryModel.getName());
        categoryRepository.delete(categoryModel);
    }

    @Override
    public Page<CategoryModel> findAllBy(Specification<CategoryModel> specification, Pageable pageable) {
        return categoryRepository.findAll(specification, pageable);
    }

    @Override
    public Set<CategoryModel> findAllByIdIn(Set<String> ids) {
        return new HashSet<>(categoryRepository.findAllById(ids));
    }

}