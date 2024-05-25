package com.lingo.craft.domain.temporal.activities.impl;

import com.lingo.craft.domain.processing.model.AccumulatedContentAnalysisEvent;
import com.lingo.craft.domain.processing.service.TextSentimentAnalysisService;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisActivity;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Component
@ActivityImpl(taskQueues = TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
public class ContentSentimentAnalysisActivityImpl implements ContentSentimentAnalysisActivity {
    private final TextSentimentAnalysisService textSentimentAnalysisService;

    public ContentSentimentAnalysisActivityImpl(TextSentimentAnalysisService textSentimentAnalysisService) {
        this.textSentimentAnalysisService = textSentimentAnalysisService;
    }

    @Override
    public AccumulatedContentAnalysisEvent publishContentSentimentEvents(
            String workflowId,
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    ) {
        return textSentimentAnalysisService.sentimentAnalysis(
                workflowId,
                contentAnalysisEvents
        );
    }
}
