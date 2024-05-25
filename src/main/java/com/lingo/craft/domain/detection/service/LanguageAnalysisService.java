package com.lingo.craft.domain.detection.service;

import com.lingo.craft.domain.detection.model.LanguageAnalysisModel;
import com.lingo.craft.domain.detection.util.TikaParserHelper;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.service.ContentAnalysisService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.lingo.craft.utils.LingoHelper.getDisplayLanguage;
import static com.lingo.craft.utils.LingoHelper.getResourcePath;

@Service
public class LanguageAnalysisService {
    private final ContentAnalysisService contentAnalysisService;

    public LanguageAnalysisService(ContentAnalysisService contentAnalysisService) {
        this.contentAnalysisService = contentAnalysisService;
    }

    public LanguageAnalysisModel detectLanguage(LanguageAnalysisModel model) throws IOException {
        var tikaParserHelper = new TikaParserHelper();
        return createLanguageAnalysisModel(
                tikaParserHelper,
                model.getText()
        );
    }

    public LanguageAnalysisModel detectLanguageFromFile(MultipartFile multipartFile) throws IOException, TikaException, SAXException {
        var tempFile = new File(getResourcePath().concat("/").concat(multipartFile.getName()));
        multipartFile.transferTo(tempFile);
        var tikaParserHelper = new TikaParserHelper();
        try (var inputStream = new FileInputStream(tempFile)) {
            tikaParserHelper.parse(inputStream);
        } finally {
            FileUtils.delete(tempFile);
        }

        return createLanguageAnalysisModel(
                tikaParserHelper,
                tikaParserHelper.getContent()
        );
    }

    private LanguageAnalysisModel createLanguageAnalysisModel(
            TikaParserHelper tikaParserHelper,
            String text
    ) {
        var languageResult = tikaParserHelper.detectLanguage(text);
        var languageCode = languageResult.getLanguage();
        var languageAnalysisModel = LanguageAnalysisModel.builder()
                .languageCode(languageCode)
                .language(getDisplayLanguage(languageCode))
                .text(text)
                .confidence(languageResult.getConfidence())
                .rawScore(languageResult.getRawScore())
                .metadata(tikaParserHelper.readMetadata())
                .build();

        languageAnalysisModel.setLanguageAnalysisWorkflowId(
                publishContentAnalysisEvents(languageAnalysisModel)
        );

        return languageAnalysisModel;
    }

    private String publishContentAnalysisEvents(LanguageAnalysisModel languageAnalysisModel) {
        return contentAnalysisService.publishContentAnalysisEvents(
                ContentSentimentAnalysisEvent.builder()
                        .languageCode(languageAnalysisModel.getLanguageCode())
                        .text(languageAnalysisModel.getText())
                        .build()
        );
    }
}

