package com.smsmode.media.dao.repository;

import com.smsmode.media.model.MediaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, String>, JpaSpecificationExecutor<MediaModel> {
}