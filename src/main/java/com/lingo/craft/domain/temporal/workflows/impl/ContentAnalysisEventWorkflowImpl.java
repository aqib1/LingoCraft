package com.lingo.craft.domain.temporal.workflows.impl;

import com.lingo.craft.domain.temporal.activities.stub.ActivityStubs;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisActivity;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.workflows.ContentAnalysisEventWorkflow;
import io.temporal.failure.ApplicationFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_QUEUE;

@WorkflowImpl(taskQueues = TASK_CONTENT_SEMANTIC_QUEUE)
public class ContentAnalysisEventWorkflowImpl implements ContentAnalysisEventWorkflow {
    private static final String LANGUAGE_PROCESSING_EVENT_PUBLISHING_EXCEPTION = "LANGUAGE_PROCESSING_EVENT_PUBLISHING_EXCEPTION";
    private final List<ContentSentimentAnalysisEvent> contentAnalysisEvents = new CopyOnWriteArrayList<>();
    private ContentSentimentAnalysisActivity contentSentimentAnalysisActivity;
    private boolean exit = false;

    @Override
    public void startWorkflow() {
        try {
            this.contentSentimentAnalysisActivity = new ActivityStubs().getContentSentimentAnalysisActivity();
            run();
        } catch (Exception exception) {
            throw ApplicationFailure.newFailureWithCause(
                    String.format(
                            "Unexpected exception while executing %s",
                            ContentAnalysisEventWorkflow.class.getName()
                    ),
                    LANGUAGE_PROCESSING_EVENT_PUBLISHING_EXCEPTION,
                    exception
            );
        }
    }

    @Override
    public void publishContentAnalysisEvents(List<ContentSentimentAnalysisEvent> contentAnalysisEvents) {
        this.contentAnalysisEvents.addAll(contentAnalysisEvents);
    }

    @Override
    public void exist() {
        exit = true;
    }

    private void run() {
        while (true) {
            Workflow.await(() -> !contentAnalysisEvents.isEmpty() || exit);
            if (contentAnalysisEvents.isEmpty() && exit) {
                return;
            }
            var immutableEventList = contentAnalysisEvents.stream().toList();
            contentAnalysisEvents.clear();
            contentSentimentAnalysisActivity.publishContentSentimentEvents(immutableEventList);
        }
    }
}
