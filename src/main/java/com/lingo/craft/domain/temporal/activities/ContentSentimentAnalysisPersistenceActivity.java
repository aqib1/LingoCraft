package com.lingo.craft.domain.temporal.activities;

import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import io.temporal.activity.ActivityInterface;


@ActivityInterface
public interface ContentSentimentAnalysisPersistenceActivity {

    void contentSentimentAnalysisPersistenceEvents(
            AccumulatedContentSentimentModel accumulatedContentSentimentModel
    );
}
