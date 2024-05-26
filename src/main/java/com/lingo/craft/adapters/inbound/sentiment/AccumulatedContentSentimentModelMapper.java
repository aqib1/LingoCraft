package com.lingo.craft.adapters.inbound.sentiment;

import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import org.mapstruct.Mapper;
import com.diabolocom.release.openapi.model.ContentSentimentResponse;

@Mapper(componentModel = "spring")
public interface AccumulatedContentSentimentModelMapper {

    ContentSentimentResponse toContentSentimentResponse(
            AccumulatedContentSentimentModel accumulatedContentSentimentModel
    );
}
