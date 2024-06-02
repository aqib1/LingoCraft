package com.lingo.craft.domain.temporal.activities.impl;

import com.lingo.craft.domain.temporal.activities.ContentTranslationActivity;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.mapper.ContentSentimentAnalysisEventMapper;
import com.lingo.craft.domain.translation.mapper.ContentTranslationInputMapper;
import com.lingo.craft.domain.translation.service.ContentTranslationService;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.lingo.craft.utils.LingoHelper.EN_LANGUAGE_CODE;
import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Component
@ActivityImpl(taskQueues = TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE)
public class ContentTranslationActivityImpl implements ContentTranslationActivity {
    private final ContentTranslationService contentTranslationService;
    private final ContentTranslationInputMapper contentTranslationInputMapper;
    private final ContentSentimentAnalysisEventMapper contentSentimentAnalysisEventMapper;

    public ContentTranslationActivityImpl(
            ContentTranslationService contentTranslationService,
            ContentTranslationInputMapper contentTranslationInputMapper,
            ContentSentimentAnalysisEventMapper contentSentimentAnalysisEventMapper
    ) {
        this.contentTranslationService = contentTranslationService;
        this.contentTranslationInputMapper = contentTranslationInputMapper;
        this.contentSentimentAnalysisEventMapper = contentSentimentAnalysisEventMapper;
    }

    @Override
    public List<ContentSentimentAnalysisEvent> translateContent(
            List<ContentSentimentAnalysisEvent> contentSentimentAnalysisEvents
    ) {
        var prepareEnglishContentSentimentAnalysis = new ArrayList<>(contentSentimentAnalysisEvents.stream()
                .filter(contentSentimentAnalysisEvent -> contentSentimentAnalysisEvent.getLanguageCode().equals(EN_LANGUAGE_CODE))
                .toList());

        var contentTranslationInputListForNonEnglishLanguages = contentTranslationInputMapper.mapToContentTranslationInputList(
                contentSentimentAnalysisEvents
                        .stream()
                        .filter(contentSentimentAnalysisEvent -> !contentSentimentAnalysisEvent.getLanguageCode().equals(EN_LANGUAGE_CODE))
                        .toList()
        );

        prepareEnglishContentSentimentAnalysis.addAll(
                contentSentimentAnalysisEventMapper.mapToContentSentimentAnalysisEventList(
                        contentTranslationService.translateContent(contentTranslationInputListForNonEnglishLanguages)
                )
        );

        return prepareEnglishContentSentimentAnalysis;
    }
}
