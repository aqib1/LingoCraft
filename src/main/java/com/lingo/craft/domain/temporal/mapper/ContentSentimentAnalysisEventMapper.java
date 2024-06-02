package com.lingo.craft.domain.temporal.mapper;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.translation.model.ContentTranslationOutput;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentSentimentAnalysisEventMapper {
    ContentSentimentAnalysisEvent mapToContentSentimentAnalysisEvent(
            ContentTranslationOutput contentTranslationOutput
    );

    List<ContentSentimentAnalysisEvent> mapToContentSentimentAnalysisEventList(
            List<ContentTranslationOutput> contentTranslationOutputs
    );
}
