package com.lingo.craft.domain.temporal.activities;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.activity.ActivityInterface;

import java.util.List;

@ActivityInterface
public interface ContentTranslationActivity {
    List<ContentSentimentAnalysisEvent> translateContent(
            List<ContentSentimentAnalysisEvent> contentSentimentAnalysisEvents
    );
}
