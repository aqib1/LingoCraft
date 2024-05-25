package com.lingo.craft.domain.detection.model;

import lombok.*;
import org.apache.tika.language.detect.LanguageConfidence;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LanguageAnalysisModel {
    private String languageAnalysisWorkflowId;
    private String text;
    private String languageCode;
    private String language;
    private LanguageConfidence confidence;
    private float rawScore;
    private Map<String, String> metadata;
}
