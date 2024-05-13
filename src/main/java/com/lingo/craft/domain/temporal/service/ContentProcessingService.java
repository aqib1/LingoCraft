package com.lingo.craft.domain.temporal.service;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.workflows.ContentAnalysisEventWorkflow;
import com.lingo.craft.domain.temporal.workflows.configuration.WorkflowsConfiguration;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_QUEUE;

@Service
public class ContentProcessingService {
    private final WorkflowClient workflowClient;
    private final WorkflowsConfiguration configuration;

    public ContentProcessingService(
            WorkflowClient workflowClient,
            WorkflowsConfiguration configuration
    ) {
        this.workflowClient = workflowClient;
        this.configuration = configuration;
    }

    public void publishContentAnalysisEvents(
            ContentSentimentAnalysisEvent event
    ) {
        publishContentAnalysisEvents(List.of(event));
    }

    public void publishContentAnalysisEvents(
            List<ContentSentimentAnalysisEvent> events
    ) {
        var contentAnalysisEventWorkflow = initContentAnalysisEventWorkflow();

        contentAnalysisEventWorkflow.publishContentAnalysisEvents(events);
        contentAnalysisEventWorkflow.exist();
    }

    private ContentAnalysisEventWorkflow initContentAnalysisEventWorkflow() {
        var contentAnalysisEventWorkflow = workflowClient.newWorkflowStub(
                ContentAnalysisEventWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId(configuration.getContentAnalysisEventWorkflow()
                                .getWorkflowIdPrefix()
                                .concat(UUID.randomUUID().toString()))
                        .setTaskQueue(TASK_CONTENT_SEMANTIC_QUEUE)
                        .build()
        );

        WorkflowClient.start(contentAnalysisEventWorkflow::startWorkflow);
        return contentAnalysisEventWorkflow;
    }
}
