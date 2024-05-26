package com.lingo.craft.domain.temporal.service;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.workflows.ContentAnalysisEventWorkflow;
import com.lingo.craft.domain.temporal.workflows.configuration.WorkflowsConfiguration;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Service
public class ContentAnalysisWorkflowService {
    private final WorkflowClient workflowClient;
    private final WorkflowsConfiguration configuration;

    public ContentAnalysisWorkflowService(
            WorkflowClient workflowClient,
            WorkflowsConfiguration configuration
    ) {
        this.workflowClient = workflowClient;
        this.configuration = configuration;
    }

    public String publishContentAnalysisEvents(
            String userId,
            ContentSentimentAnalysisEvent event
    ) {
        return publishContentAnalysisEvents(
                userId,
                List.of(event)
        );
    }

    public String publishContentAnalysisEvents(
            String userId,
            List<ContentSentimentAnalysisEvent> events
    ) {
        var contentAnalysisEventWorkflow = initContentAnalysisEventWorkflow(
                userId
        );
        var workflowId = contentAnalysisEventWorkflow.getWorkflowId();
        contentAnalysisEventWorkflow.publishContentAnalysisEvents(events);
        contentAnalysisEventWorkflow.exist();
        return workflowId;
    }

    private ContentAnalysisEventWorkflow initContentAnalysisEventWorkflow(
            String userId
    ) {
        var contentAnalysisEventWorkflow = workflowClient.newWorkflowStub(
                ContentAnalysisEventWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId(configuration.getContentAnalysisEventWorkflow()
                                .getWorkflowIdPrefix()
                                .concat(UUID.randomUUID().toString()))
                        .setTaskQueue(TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
                        .build()
        );

        WorkflowClient.start(
                contentAnalysisEventWorkflow::startWorkflow,
                UUID.fromString(userId)
        );

        return contentAnalysisEventWorkflow;
    }
}
