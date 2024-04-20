package com.lingo.craft.domain.detection.model;

import lombok.*;
import org.apache.tika.language.detect.LanguageConfidence;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LanguageDetectionModel {
    private String text;
    private String languageCode;
    private LanguageConfidence confidence;
    private float rawScore;
}
