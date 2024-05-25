package com.lingo.craft.domain.processing.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AccumulatedContentAnalysisEvent implements Serializable {
    private String workflowId;
    private List<ContentSentimentAnalysisModel> contentSentimentAnalysisModels;
    private int accumulatedContentScore;
    private String positiveSentimentPercentage;
    private ContentSentimentScore accumulatedContentSentimentScore;
}
