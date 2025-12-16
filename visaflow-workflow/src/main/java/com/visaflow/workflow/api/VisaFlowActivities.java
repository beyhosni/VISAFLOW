package com.visaflow.workflow.api;

import com.visaflow.rules.domain.ValidationResult;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.List;
import java.util.UUID;

@ActivityInterface
public interface VisaFlowActivities {

    @ActivityMethod
    void updateCaseStatus(UUID caseId, String status);

    @ActivityMethod
    List<ValidationResult> validateCase(UUID caseId);

    @ActivityMethod
    void generateSubmissionPackage(UUID caseId);
    
    @ActivityMethod
    void sendNotification(UUID caseId, String message);
}
