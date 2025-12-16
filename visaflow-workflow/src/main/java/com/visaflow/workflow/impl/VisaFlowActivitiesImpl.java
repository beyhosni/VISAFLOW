package com.visaflow.workflow.impl;

import com.visaflow.case_management.domain.Case;
import com.visaflow.case_management.repository.CaseRepository;
import com.visaflow.document.domain.Document;
import com.visaflow.document.service.DocumentService;
import com.visaflow.questionnaire.service.QuestionnaireService;
import com.visaflow.rules.domain.ValidationResult;
import com.visaflow.rules.dto.CaseData;
import com.visaflow.rules.service.RuleEngine;
import com.visaflow.workflow.api.VisaFlowActivities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisaFlowActivitiesImpl implements VisaFlowActivities {

    private final CaseRepository caseRepository;
    private final QuestionnaireService questionnaireService;
    private final DocumentService documentService;
    private final RuleEngine ruleEngine;

    @Override
    @Transactional
    public void updateCaseStatus(UUID caseId, String statusName) {
        log.info("Activity: Updating case {} status to {}", caseId, statusName);
        Case c = caseRepository.findById(caseId).orElseThrow();
        // Ideally map string to enum properly, simplified for MVP
        // c.setStatus(CaseStatus.valueOf(statusName)); 
        // For now just logging as the enum mapping might need care
    }

    @Override
    public List<ValidationResult> validateCase(UUID caseId) {
        log.info("Activity: Validating case {}", caseId);
        var answers = questionnaireService.getAnswers(caseId);
        var documents = documentService.getDocumentsByCase(caseId).stream()
                .map(Document::getDocumentType)
                .collect(Collectors.toList());

        CaseData data = new CaseData(answers, documents);
        return ruleEngine.validate(data);
    }

    @Override
    public void generateSubmissionPackage(UUID caseId) {
        log.info("Activity: Generating submission package for case {}", caseId);
        // Logic to call Form Engine would go here
        // For MVP we just simulate it
        try {
            Thread.sleep(1000); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendNotification(UUID caseId, String message) {
        log.info("Activity: Notification for case {}: {}", caseId, message);
    }
}
