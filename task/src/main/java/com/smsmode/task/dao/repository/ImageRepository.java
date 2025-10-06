package com.smsmode.task.dao.repository;

import com.smsmode.task.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, String>, JpaSpecificationExecutor<ImageModel> {
}
