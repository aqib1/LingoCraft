package com.lingo.craft.domain.translation.mapper;

import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.translation.model.ContentTranslationInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static com.lingo.craft.utils.LingoHelper.EN_LANGUAGE_CODE;

@Mapper(componentModel = "spring")
public interface ContentTranslationInputMapper {

    @Mapping(target = "targetLanguageCode", constant = EN_LANGUAGE_CODE)
    ContentTranslationInput mapToContentTranslationInput(
            ContentSentimentAnalysisEvent contentSentimentAnalysisEvent
    );

    List<ContentTranslationInput> mapToContentTranslationInputList(
            List<ContentSentimentAnalysisEvent> contentSentimentAnalysisEvents
    );
}
