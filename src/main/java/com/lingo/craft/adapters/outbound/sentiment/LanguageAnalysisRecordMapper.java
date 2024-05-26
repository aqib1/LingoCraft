package com.lingo.craft.adapters.outbound.sentiment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import com.lingo.craft.domain.processing.model.ContentSentimentAnalysisModel;
import com.lingo.craft.tables.records.LanguageAnalysisRecord;
import org.jooq.JSONB;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LanguageAnalysisRecordMapper {
    @Autowired
    private ObjectMapper objectMapper;

    @Mapping(source = "workflowId", target = "id")
    @Mapping(source = "userId", target = "userid")
    @Mapping(source = "accumulatedContentSentimentScore", target = "accumulatedcontentsentimentscore")
    @Mapping(source = "positiveSentimentPercentage", target = "positivesentimentpercentage")
    @Mapping(
            source = "accumulatedContentSentiment",
            target = "accumulatedcontentsentiment"
    )
    @Mapping(
            source = "contentSentimentAnalysisModels",
            target = "contentsentimentanalysismodels",
            qualifiedByName = "toJSONB"
    )
    public abstract LanguageAnalysisRecord toLanguageAnalysisRecord(
            AccumulatedContentSentimentModel accumulatedContentSentimentModel
    );

    @Mapping(source = "id", target = "workflowId")
    @Mapping(source = "userid", target = "userId")
    @Mapping(source = "accumulatedcontentsentimentscore", target = "accumulatedContentSentimentScore")
    @Mapping(source = "positivesentimentpercentage", target = "positiveSentimentPercentage")
    @Mapping(
            source = "accumulatedcontentsentiment",
            target = "accumulatedContentSentiment"
    )
    @Mapping(
            source = "contentsentimentanalysismodels",
            target = "contentSentimentAnalysisModels",
            qualifiedByName = "toContentSentimentAnalysisModels"
    )
    public abstract AccumulatedContentSentimentModel toAccumulatedContentAnalysisModel(
            LanguageAnalysisRecord languageAnalysisRecord
    );

    @Named("toJSONB")
    public JSONB toJSONB(
            List<ContentSentimentAnalysisModel> contentSentimentAnalysisModels
    ) throws JsonProcessingException {
        return JSONB.jsonb(
                objectMapper.writeValueAsString(contentSentimentAnalysisModels)
        );
    }

    @Named("toContentSentimentAnalysisModels")
    public List<ContentSentimentAnalysisModel> toContentSentimentAnalysisModels(
            JSONB jsonb
    ) throws JsonProcessingException {
        return objectMapper.readValue(
                jsonb.data(),
                new TypeReference<>() {
                }
        );
    }
}
