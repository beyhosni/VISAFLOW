package com.visaflow.workflow.impl;

import com.visaflow.rules.domain.ValidationResult;
import com.visaflow.workflow.api.VisaFlowActivities;
import com.visaflow.workflow.api.VisaFlowWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class VisaFlowWorkflowImpl implements VisaFlowWorkflow {

    private final VisaFlowActivities activities = Workflow.newActivityStub(
            VisaFlowActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(60))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build());

    private final Logger logger = Workflow.getLogger(VisaFlowWorkflowImpl.class);

    private String status = "STARTED";
    private boolean answersSubmitted = false;
    private boolean documentsUploaded = false; // Simplified for MVP

    @Override
    public void execute(UUID caseId) {
        logger.info("Workflow started for case: {}", caseId);
        
        // 1. Initialize
        status = "DRAFT";
        activities.updateCaseStatus(caseId, "DRAFT");

        // 2. Wait for Data Collection (Signals)
        Workflow.await(() -> answersSubmitted);  // Wait for signal
        
        status = "IN_PROGRESS";
        activities.updateCaseStatus(caseId, "IN_PROGRESS");

        // 3. Validation
        status = "VALIDATING";
        List<ValidationResult> validationResults = activities.validateCase(caseId);

        if (!validationResults.isEmpty()) {
            status = "VALIDATION_FAILED";
            activities.updateCaseStatus(caseId, "VALIDATION_PENDING"); // Ask user to fix
            // In a real flow, we would loop back or wait for corrections
            logger.warn("Validation failed for case {}", caseId);
            return;
        }

        // 4. Generation
        status = "GENERATING_PACKAGE";
        activities.generateSubmissionPackage(caseId);

        // 5. Completion
        status = "READY";
        activities.updateCaseStatus(caseId, "READY_TO_SUBMIT");
        
        logger.info("Workflow completed for case: {}", caseId);
    }

    @Override
    public void submitAnswers() {
        this.answersSubmitted = true;
    }

    @Override
    public void uploadDocument() {
        this.documentsUploaded = true;
        // In real app, we might wait for ALL required docs
    }

    @Override
    public String getCurrentStatus() {
        return this.status;
    }
}
