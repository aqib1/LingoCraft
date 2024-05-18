package com.lingo.craft.domain.temporal.activities;

import com.lingo.craft.domain.processing.model.ContentSentimentAnalysisModel;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface ContentSentimentAnalysisActivity {

    List<ContentSentimentAnalysisModel> publishContentSentimentEvents(
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    );
}
