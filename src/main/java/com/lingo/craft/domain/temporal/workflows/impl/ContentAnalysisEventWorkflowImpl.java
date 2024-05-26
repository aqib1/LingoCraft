package com.lingo.craft.domain.temporal.workflows.impl;

import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisPersistenceActivity;
import com.lingo.craft.domain.temporal.activities.stub.ActivityStubs;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisActivity;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.workflows.ContentAnalysisEventWorkflow;
import io.temporal.failure.ApplicationFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@WorkflowImpl(taskQueues = TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
public class ContentAnalysisEventWorkflowImpl implements ContentAnalysisEventWorkflow {
    private static final String CONTENT_ANALYSIS_EVENT_PUBLISHING_EXCEPTION = "CONTENT_ANALYSIS_EVENT_PUBLISHING_EXCEPTION";
    private final List<ContentSentimentAnalysisEvent> contentSentimentAnalysisEvents = new CopyOnWriteArrayList<>();
    private ContentSentimentAnalysisActivity contentSentimentAnalysisActivity;
    private ContentSentimentAnalysisPersistenceActivity contentSentimentAnalysisPersistenceActivity;
    private boolean exit = false;
    private String workflowId;
    private UUID userId;

    @Override
    public void startWorkflow(UUID userId) {
        try {
            this.userId = userId;
            var activityStubs = new ActivityStubs();
            this.contentSentimentAnalysisActivity = activityStubs.getContentSentimentAnalysisActivity();
            this.contentSentimentAnalysisPersistenceActivity = activityStubs.getContentSentimentAnalysisPersistenceActivity();
            this.workflowId = Workflow.getInfo().getRunId();
            runContentSentimentAnalysisAndPersistWorkflow();
        } catch (Exception exception) {
            throw ApplicationFailure.newFailureWithCause(
                    String.format(
                            "Unexpected exception while executing %s",
                            ContentAnalysisEventWorkflow.class.getName()
                    ),
                    CONTENT_ANALYSIS_EVENT_PUBLISHING_EXCEPTION,
                    exception
            );
        }
    }

    @Override
    public void publishContentAnalysisEvents(List<ContentSentimentAnalysisEvent> contentAnalysisEvents) {
        this.contentSentimentAnalysisEvents.addAll(contentAnalysisEvents);
    }

    @Override
    public void exist() {
        exit = true;
    }

    @Override
    public String getWorkflowId() {
        return this.workflowId;
    }

    private void runContentSentimentAnalysisAndPersistWorkflow() {
        while (true) {
            Workflow.await(() -> !contentSentimentAnalysisEvents.isEmpty() || exit);
            if (contentSentimentAnalysisEvents.isEmpty() && exit) {
                return;
            }
            var immutableEventList = contentSentimentAnalysisEvents.stream().toList();
            contentSentimentAnalysisEvents.clear();
            var accumulatedContentAnalysis = contentSentimentAnalysisActivity.publishContentSentimentEvents(
                    workflowId,
                    immutableEventList
            );

            accumulatedContentAnalysis.setUserId(userId);
            contentSentimentAnalysisPersistenceActivity.contentSentimentAnalysisPersistenceEvents(
                    accumulatedContentAnalysis
            );
        }
    }
}
