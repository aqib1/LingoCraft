package com.lingo.craft.domain.temporal.activities;

import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface ContentSentimentAnalysisActivity {

    AccumulatedContentSentimentModel publishContentSentimentEvents(
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    );
}
