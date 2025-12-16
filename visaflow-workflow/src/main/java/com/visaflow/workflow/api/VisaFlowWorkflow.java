package com.visaflow.workflow.api;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.QueryMethod;

import java.util.UUID;

@WorkflowInterface
public interface VisaFlowWorkflow {

    @WorkflowMethod
    void execute(UUID caseId);

    @SignalMethod
    void submitAnswers();

    @SignalMethod
    void uploadDocument();

    @QueryMethod
    String getCurrentStatus();
}
