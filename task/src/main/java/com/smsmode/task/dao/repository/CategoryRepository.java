package com.smsmode.task.dao.repository;

import com.smsmode.task.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, String>, JpaSpecificationExecutor<CategoryModel> {
}

