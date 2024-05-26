package com.lingo.craft.domain.temporal.activities.impl;

import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import com.lingo.craft.domain.sentiment.service.ContentSentimentService;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisPersistenceActivity;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Component
@ActivityImpl(taskQueues = TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
public class ContentSentimentAnalysisPersistenceActivityImpl implements ContentSentimentAnalysisPersistenceActivity {
    private final ContentSentimentService contentSentimentService;

    public ContentSentimentAnalysisPersistenceActivityImpl(
            ContentSentimentService contentSentimentService
    ) {
        this.contentSentimentService = contentSentimentService;
    }

    @Override
    public void contentSentimentAnalysisPersistenceEvents(
            AccumulatedContentSentimentModel accumulatedContentSentimentModel
    ) {
        contentSentimentService.create(
                accumulatedContentSentimentModel
        );
    }
}
