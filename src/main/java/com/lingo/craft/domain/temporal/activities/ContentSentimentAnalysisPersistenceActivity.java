package com.lingo.craft.domain.temporal.activities;

import com.lingo.craft.domain.processing.model.AccumulatedContentAnalysisEvent;
import io.temporal.activity.ActivityInterface;


@ActivityInterface
public interface ContentSentimentAnalysisPersistenceActivity {

    void contentSentimentAnalysisPersistenceEvents(
            AccumulatedContentAnalysisEvent accumulatedContentAnalysisEvent
    );
}
