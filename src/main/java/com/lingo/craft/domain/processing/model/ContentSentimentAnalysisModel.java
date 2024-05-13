package com.lingo.craft.domain.processing.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ContentSentimentAnalysisModel implements Serializable {
    private String text;
    private String languageCode;
    private int sentimentScore;
    private String sentimentName;
}
