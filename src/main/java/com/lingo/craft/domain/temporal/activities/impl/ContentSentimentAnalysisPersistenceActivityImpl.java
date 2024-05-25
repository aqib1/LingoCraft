package com.lingo.craft.domain.temporal.activities.impl;

import com.lingo.craft.domain.processing.model.AccumulatedContentAnalysisEvent;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisPersistenceActivity;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;


import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Component
@ActivityImpl(taskQueues = TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
public class ContentSentimentAnalysisPersistenceActivityImpl implements ContentSentimentAnalysisPersistenceActivity {
    @Override
    public void contentSentimentAnalysisPersistenceEvents(AccumulatedContentAnalysisEvent accumulatedContentAnalysisEvent) {
        System.out.println(accumulatedContentAnalysisEvent);
    }
}
