package com.lingo.craft.domain.temporal.activities.impl;

import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import com.lingo.craft.domain.processing.service.ContentSentimentAnalysisService;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisActivity;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Component
@ActivityImpl(taskQueues = TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
public class ContentSentimentAnalysisActivityImpl implements ContentSentimentAnalysisActivity {
    private final ContentSentimentAnalysisService contentSentimentAnalysisService;

    public ContentSentimentAnalysisActivityImpl(ContentSentimentAnalysisService contentSentimentAnalysisService) {
        this.contentSentimentAnalysisService = contentSentimentAnalysisService;
    }

    @Override
    public AccumulatedContentSentimentModel publishContentSentimentEvents(
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    ) {
        return contentSentimentAnalysisService.sentimentAnalysis(
                contentAnalysisEvents
        );
    }
}
