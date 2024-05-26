package com.lingo.craft.domain.temporal.workflows;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.workflow.*;

import java.util.List;
import java.util.UUID;

@WorkflowInterface
public interface ContentAnalysisEventWorkflow {
    @WorkflowMethod
    void startWorkflow(UUID userId);

    @SignalMethod
    void publishContentAnalysisEvents(List<ContentSentimentAnalysisEvent> contentAnalysisEvents);

    @SignalMethod
    void exist();

    @QueryMethod
    String getWorkflowId();
}
