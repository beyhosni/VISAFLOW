package com.visaflow.questionnaire.repository;

import com.visaflow.questionnaire.domain.QuestionnaireSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface QuestionnaireSubmissionRepository extends JpaRepository<QuestionnaireSubmission, UUID> {
    Optional<QuestionnaireSubmission> findByCaseId(UUID caseId);
}
