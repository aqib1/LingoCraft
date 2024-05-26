package com.lingo.craft.adapters.outbound.sentiment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lingo.craft.domain.sentiment.model.AccumulatedContentSentimentModel;
import com.lingo.craft.domain.sentiment.ports.outbound.ContentSentimentRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.lingo.craft.Tables.LANGUAGE_ANALYSIS;

@Repository
public class JdbcContentSentimentRepository implements ContentSentimentRepository {

    private final DSLContext dslContext;
    private final LanguageAnalysisRecordMapper languageAnalysisRecordMapper;

    public JdbcContentSentimentRepository(
            DSLContext dslContext,
            LanguageAnalysisRecordMapper languageAnalysisRecordMapper
    ) {
        this.dslContext = dslContext;
        this.languageAnalysisRecordMapper = languageAnalysisRecordMapper;
    }

    @Override
    @Transactional
    public Optional<AccumulatedContentSentimentModel> create(
            AccumulatedContentSentimentModel model
    ) throws JsonProcessingException {
        var optionalAnalysisRecord = dslContext.insertInto(LANGUAGE_ANALYSIS)
                .set(languageAnalysisRecordMapper.toLanguageAnalysisRecord(model))
                .returning()
                .fetchOptional();

        return optionalAnalysisRecord.map(languageAnalysisRecordMapper::toAccumulatedContentAnalysisModel);
    }

    @Override
    @Transactional
    public Optional<AccumulatedContentSentimentModel> getByIdAndUserId(UUID id, UUID userId) {
        var optionalAnalysisRecord = dslContext.selectFrom(LANGUAGE_ANALYSIS)
                .where(LANGUAGE_ANALYSIS.ID.eq(id))
                .fetchOptional();

        return optionalAnalysisRecord.map(languageAnalysisRecordMapper::toAccumulatedContentAnalysisModel);
    }
}
