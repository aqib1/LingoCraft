package com.lingo.craft.domain.detection.service;

import com.lingo.craft.domain.detection.model.LanguageDetectionModel;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.stereotype.Service;

@Service
public class LanguageDetectionService {
    private final LanguageDetector languageDetector;

    public LanguageDetectionService(LanguageDetector languageDetector) {
        this.languageDetector = languageDetector;
    }

    public LanguageDetectionModel detectLanguage(LanguageDetectionModel model) {
        var languageResult = languageDetector.detect(model.getText());

        return LanguageDetectionModel.builder()
                .languageCode(languageResult.getLanguage())
                .text(model.getText())
                .confidence(languageResult.getConfidence())
                .rawScore(languageResult.getRawScore())
                .build();
    }
}
