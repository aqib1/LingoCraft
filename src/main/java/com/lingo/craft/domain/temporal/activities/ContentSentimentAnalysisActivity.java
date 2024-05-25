package com.lingo.craft.domain.temporal.activities;

import com.lingo.craft.domain.processing.model.AccumulatedContentAnalysisEvent;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface ContentSentimentAnalysisActivity {

    AccumulatedContentAnalysisEvent publishContentSentimentEvents(
            String workflowId,
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    );
}
