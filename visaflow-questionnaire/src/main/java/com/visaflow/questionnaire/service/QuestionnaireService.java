package com.visaflow.questionnaire.service;

import com.visaflow.questionnaire.domain.QuestionnaireSubmission;
import com.visaflow.questionnaire.repository.QuestionnaireSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionnaireSubmissionRepository repository;

    @Transactional
    public QuestionnaireSubmission saveAnswers(UUID caseId, Map<String, Object> answers) {
        QuestionnaireSubmission submission = repository.findByCaseId(caseId)
                .orElseGet(() -> {
                    QuestionnaireSubmission newSub = new QuestionnaireSubmission();
                    newSub.setCaseId(caseId);
                    // Default type for MVP
                    newSub.setQuestionnaireType("I-130-PETITIONER"); 
                    return newSub;
                });
        
        // Merge or replace logic can go here. For now, we replace/update.
        if (submission.getAnswers() == null) {
            submission.setAnswers(answers);
        } else {
            submission.getAnswers().putAll(answers);
        }
        
        return repository.save(submission);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAnswers(UUID caseId) {
        return repository.findByCaseId(caseId)
                .map(QuestionnaireSubmission::getAnswers)
                .orElse(Map.of());
    }
}
