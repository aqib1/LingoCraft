package com.lingo.craft.domain.sentiment.ports.outbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;

import java.util.Optional;
import java.util.UUID;

public interface ContentSentimentRepository {
    Optional<AccumulatedContentSentimentModel> create(
            AccumulatedContentSentimentModel model
    ) throws JsonProcessingException;

    Optional<AccumulatedContentSentimentModel> getByIdAndUserId(
            UUID id,
            UUID userId
    );
}
