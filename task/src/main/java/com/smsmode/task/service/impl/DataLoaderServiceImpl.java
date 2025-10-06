package com.smsmode.task.service.impl;

import com.smsmode.task.dao.repository.CategoryRepository;
import com.smsmode.task.dao.specification.CategorySpecification;
import com.smsmode.task.model.CategoryModel;
import com.smsmode.task.service.DataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService, CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void populateCategories() {
        List<String> categoryNames = Arrays.asList(
                "Equipment",
                "Facilities",
                "Guest",
                "Housekeeping",
                "Maintenance",
                "Safety",
                "Supplies",
                "Utilities"
        );

        for (String name : categoryNames) {
            if (!categoryRepository.exists(CategorySpecification.withNameLike(name))) {
                CategoryModel category = new CategoryModel();
                category.setName(name);
                categoryRepository.save(category);
                log.info("Category '{}' created.", name);
            } else {
                log.info("Category '{}' already exists. Skipping.", name);
            }
        }
    }

    @Override
    public void run(String... args) {
        this.populateCategories();
    }
}
