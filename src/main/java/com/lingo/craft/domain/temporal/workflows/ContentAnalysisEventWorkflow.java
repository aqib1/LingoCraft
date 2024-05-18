package com.lingo.craft.domain.temporal.workflows;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.List;

@WorkflowInterface
public interface ContentAnalysisEventWorkflow {
    @WorkflowMethod
    void startWorkflow();

    @SignalMethod
    void publishContentAnalysisEvents(List<ContentSentimentAnalysisEvent> contentAnalysisEvents);

    @SignalMethod
    void exist();
}
