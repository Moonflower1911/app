package com.smsmode.guest.dao.repository;

import com.smsmode.guest.model.PartyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Guest entity operations.
 * Provides CRUD operations and query capabilities for Guest entities.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Jun 2025</p>
 */
@Repository
public interface PartyRepository extends JpaRepository<PartyModel, String>, JpaSpecificationExecutor<PartyModel> {
}