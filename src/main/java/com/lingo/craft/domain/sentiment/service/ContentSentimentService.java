package com.lingo.craft.domain.sentiment.service;

import com.lingo.craft.domain.sentiment.exception.ContentSentimentNotFoundException;
import com.lingo.craft.domain.sentiment.exception.ContentSentimentPersistenceFailure;
import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import com.lingo.craft.domain.sentiment.ports.outbound.ContentSentimentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ContentSentimentService {
    private final ContentSentimentRepository contentSentimentRepository;

    public ContentSentimentService(ContentSentimentRepository contentSentimentRepository) {
        this.contentSentimentRepository = contentSentimentRepository;
    }

    public AccumulatedContentSentimentModel getByIdAndUserId(
            UUID userId,
            UUID workflowId
    ) {
        Optional<AccumulatedContentSentimentModel> fetched = contentSentimentRepository
                .getByIdAndUserId(
                        workflowId,
                        userId
                );

        if (fetched.isEmpty()) {
            throw new ContentSentimentNotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Accumulated content sentiment not found"
            );
        }

        return fetched.get();
    }

    public AccumulatedContentSentimentModel create(
            String workflowId,
            AccumulatedContentSentimentModel accumulatedContentSentimentModel
    ) {
        Optional<AccumulatedContentSentimentModel> created;

        try {
            accumulatedContentSentimentModel.setWorkflowId(UUID.fromString(workflowId));
            created = contentSentimentRepository.create(
                    accumulatedContentSentimentModel
            );
        } catch (Exception ex) {
            throw new ContentSentimentPersistenceFailure(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage(),
                    ex
            );
        }

        if (created.isEmpty()) {
            throw new ContentSentimentPersistenceFailure(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while persisting content analysis in database"
            );
        }
        return created.get();
    }
}
