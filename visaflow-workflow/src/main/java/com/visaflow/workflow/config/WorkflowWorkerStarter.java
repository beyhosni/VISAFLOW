package com.visaflow.workflow.config;

import com.visaflow.workflow.api.VisaFlowWorkflow;
import com.visaflow.workflow.impl.VisaFlowActivitiesImpl;
import com.visaflow.workflow.impl.VisaFlowWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkflowWorkerStarter {

    private final WorkerFactory workerFactory;
    private final VisaFlowActivitiesImpl activitiesImplementation;

    @PostConstruct
    public void startWorker() {
        Worker worker = workerFactory.newWorker("VisaFlowTaskQueue");
        
        // Register Workflow
        worker.registerWorkflowImplementationTypes(VisaFlowWorkflowImpl.class);
        
        // Register Activities
        worker.registerActivitiesImplementations(activitiesImplementation);

        workerFactory.start();
        System.out.println("Temporal Worker Started on 'VisaFlowTaskQueue'");
    }
}
