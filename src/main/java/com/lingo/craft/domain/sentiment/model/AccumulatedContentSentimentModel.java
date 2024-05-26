package com.lingo.craft.domain.sentiment.model;

import com.lingo.craft.domain.processing.model.ContentSentiment;
import com.lingo.craft.domain.processing.model.ContentSentimentAnalysisModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AccumulatedContentSentimentModel implements Serializable {
    private UUID workflowId;
    private UUID userId;
    private List<ContentSentimentAnalysisModel> contentSentimentAnalysisModels;
    private int accumulatedContentSentimentScore;
    private String positiveSentimentPercentage;
    private ContentSentiment accumulatedContentSentiment;
}
