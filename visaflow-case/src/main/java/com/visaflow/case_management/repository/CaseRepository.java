package com.visaflow.case_management.repository;

import com.visaflow.case_management.domain.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<Case, UUID> {
    List<Case> findByUserId(UUID userId);
}
